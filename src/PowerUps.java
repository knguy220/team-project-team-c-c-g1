import acm.graphics.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PowerUps {
    private Player player;
    private GameApp gameApp;
    private StartGame startGame;

    // Cooldown times in milliseconds
    private static final long HAZMAT_COOLDOWN = 30000;
    private static final long FLY_SWAT_COOLDOWN = 60000;
    private static final long BUG_REPELLENT_COOLDOWN = 120000;

    private long lastHazmatUse = -HAZMAT_COOLDOWN;
    private long lastFlySwatUse = -FLY_SWAT_COOLDOWN;
    private long lastBugRepellentUse = -BUG_REPELLENT_COOLDOWN;

    public PowerUps(Player player, GameApp gameApp, StartGame startGame) {
        this.player = player;
        this.gameApp = gameApp;
        this.startGame = startGame;
    }

    public void activateHazmatSuit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHazmatUse >= HAZMAT_COOLDOWN) {
            lastHazmatUse = currentTime;

            // Create and activate the hazmat shield
            GOval shield = new GOval(player.getCenterX() - 50, player.getCenterY() - 50, 100, 100);
            shield.setColor(Color.YELLOW);
            shield.setFilled(true);
            shield.setFillColor(new Color(255, 255, 0, 128)); // Semi-transparent yellow
            gameApp.add(shield);

            long hazmatEndTime = currentTime + 20000; // 20 seconds duration
            startGame.activateHazmatShield(shield, hazmatEndTime);
        }
    }

    public void activateFlySwat() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFlySwatUse >= FLY_SWAT_COOLDOWN) {
            lastFlySwatUse = currentTime;

            // Create the Fly Swat projectile
            GOval flySwat = new GOval(player.getCenterX() - 15, player.getCenterY() - 15, 30, 30);
            flySwat.setColor(new Color(0, 255, 0)); // Bright green
            flySwat.setFilled(true);
            flySwat.setFillColor(new Color(0, 255, 0, 180)); // Semi-transparent green
            gameApp.add(flySwat);

            // Trail for Fly Swat
            List<GOval> trail = new ArrayList<>();

            // Thread for Fly Swat movement
            new Thread(() -> {
                try {
                    List<Enemy> enemiesToHit = new ArrayList<>(startGame.getConsole().getEnemies());
                    int hits = 0;

                    for (Enemy target : enemiesToHit) {
                        if (hits >= 10) break; // Limit to 10 hits
                        if (!target.isAlive()) continue; // Skip if enemy is already dead

                        // Move toward target
                        while (target.isAlive() && !flySwat.getBounds().intersects(target.getEnemyShape().getBounds())) {
                            double targetX = target.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
                            double targetY = target.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

                            double deltaX = targetX - (flySwat.getX() + flySwat.getWidth() / 2);
                            double deltaY = targetY - (flySwat.getY() + flySwat.getHeight() / 2);
                            double distance = Math.hypot(deltaX, deltaY);

                            // Normalize direction
                            double directionX = deltaX / distance;
                            double directionY = deltaY / distance;

                            // Move Fly Swat closer to the target
                            flySwat.move(directionX * 15, directionY * 15);

                            // Add trail effects
                            addFlySwatTrail(trail, flySwat);

                            Thread.sleep(15); // Smooth movement
                        }

                        // Swatting effect on hit
                        if (flySwat.getBounds().intersects(target.getEnemyShape().getBounds())) {
                            swatEnemy(target, flySwat); // Perform swat animation
                            target.updateHealth(100); // Apply damage
                            if (!target.isAlive()) {
                                startGame.getConsole().removeEnemy(target, true);
                            }
                            hits++;
                        }
                    }

                    // Cleanup: Remove Fly Swat and trail after hitting all targets
                    gameApp.remove(flySwat);
                    cleanupTrail(trail);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Swatting effect: Swat grows and shrinks while creating debris
    private void swatEnemy(Enemy target, GOval flySwat) {
        try {
            // Swat grows to simulate impact
            for (int i = 0; i < 3; i++) {
                flySwat.setSize(flySwat.getWidth() + 10, flySwat.getHeight() + 10);
                flySwat.setLocation(
                    flySwat.getX() - 5,
                    flySwat.getY() - 5
                );
                Thread.sleep(30);
            }

            // Create debris particles
            createDebris(target);

            // Swat shrinks back
            for (int i = 0; i < 3; i++) {
                flySwat.setSize(flySwat.getWidth() - 10, flySwat.getHeight() - 10);
                flySwat.setLocation(
                    flySwat.getX() + 5,
                    flySwat.getY() + 5
                );
                Thread.sleep(30);
            }

            // Turn the Fly Swat darker after hitting
            flySwat.setFillColor(new Color(50, 150, 50, 180)); // Dark green "stain"
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Create debris particles
    private void createDebris(Enemy target) {
        for (int i = 0; i < 8; i++) {
            GOval debris = new GOval(
                target.getEnemyShape().getX() + Enemy.getEnemySize() / 2,
                target.getEnemyShape().getY() + Enemy.getEnemySize() / 2,
                5,
                5
            );
            debris.setColor(Color.RED);
            debris.setFilled(true);
            gameApp.add(debris);

            // Move debris outward in random directions
            new Thread(() -> {
                try {
                    double angle = Math.random() * Math.PI * 2;
                    double speed = Math.random() * 5 + 2; // Random speed
                    for (int j = 0; j < 20; j++) { // 20 frames of movement
                        debris.move(Math.cos(angle) * speed, Math.sin(angle) * speed);
                        Thread.sleep(30);
                    }
                    gameApp.remove(debris); // Remove debris after animation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // Helper method to add trail effects
    private void addFlySwatTrail(List<GOval> trail, GOval flySwat) {
        GOval trailPiece = new GOval(
            flySwat.getX() + flySwat.getWidth() / 2 - 5,
            flySwat.getY() + flySwat.getHeight() / 2 - 5,
            10,
            10
        );
        trailPiece.setColor(new Color(0, 255, 0, 100)); // Transparent green
        trailPiece.setFilled(true);
        gameApp.add(trailPiece);
        trail.add(trailPiece);

        // Gradually fade and shrink the trail
        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) { // Fade over 10 frames
                    Thread.sleep(30);
                    trailPiece.setSize(trailPiece.getWidth() - 1, trailPiece.getHeight() - 1);
                    trailPiece.setLocation(
                        trailPiece.getX() + 0.5,
                        trailPiece.getY() + 0.5
                    );
                    Color currentColor = trailPiece.getColor();
                    trailPiece.setColor(new Color(
                        currentColor.getRed(),
                        currentColor.getGreen(),
                        currentColor.getBlue(),
                        Math.max(0, currentColor.getAlpha() - 10) // Reduce alpha
                    ));
                }
                gameApp.remove(trailPiece); // Remove trail piece
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Helper method to cleanup remaining trail
    private void cleanupTrail(List<GOval> trail) {
        for (GOval piece : trail) {
            gameApp.remove(piece);
        }
        trail.clear();
    }


    public void activateBugRepellent() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBugRepellentUse >= BUG_REPELLENT_COOLDOWN) {
            lastBugRepellentUse = currentTime;

            // Get the player's current position
            double playerX = player.getCenterX();
            double playerY = player.getCenterY();

            // Create a dark overlay to darken the screen
            GRect screenDarken = new GRect(0, 0, gameApp.getWidth(), gameApp.getHeight());
            screenDarken.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
            screenDarken.setFilled(true);
            gameApp.add(screenDarken);

            // Create the electric wave starting from the player
            GOval electricWave = new GOval(playerX - 50, playerY - 50, 100, 100);
            electricWave.setColor(new Color(0, 255, 255, 200)); // Cyan with some transparency
            electricWave.setFilled(true);
            electricWave.setFillColor(new Color(0, 128, 255, 100)); // Softer cyan inner glow
            gameApp.add(electricWave);

            // Thread for expanding wave animation
            new Thread(() -> {
                try {
                    for (int frame = 0; frame < 50; frame++) { // 50 frames of animation
                        Thread.sleep(30); // Smooth timing

                        // Expand the wave
                        double expansion = frame * 10; // Faster expansion
                        electricWave.setSize(
                            electricWave.getWidth() + expansion,
                            electricWave.getHeight() + expansion
                        );
                        electricWave.setLocation(
                            playerX - electricWave.getWidth() / 2,
                            playerY - electricWave.getHeight() / 2
                        );

                        // Add a pulsing effect to the wave's color
                        Color currentColor = electricWave.getFillColor();
                        int alpha = Math.max(0, currentColor.getAlpha() - 3); // Reduce alpha gradually
                        int pulse = (int) (50 * Math.sin(frame * 0.3)); // Oscillating brightness
                        electricWave.setFillColor(new Color(
                            clampColorValue(currentColor.getRed() + pulse),
                            clampColorValue(currentColor.getGreen() + pulse),
                            clampColorValue(currentColor.getBlue() + pulse),
                            alpha
                        ));
                        electricWave.setColor(new Color(
                            clampColorValue(currentColor.getRed()),
                            clampColorValue(currentColor.getGreen()),
                            clampColorValue(currentColor.getBlue()),
                            alpha
                        ));
                    }

                    // Remove the wave and fade out the dark overlay
                    gameApp.remove(electricWave);
                    fadeOutScreenDarken(screenDarken);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // Optional: Play sound if available
            if (gameApp.getBugRepellentSound() != null) {
                try {
                    gameApp.getBugRepellentSound().play();
                } catch (Exception e) {
                    System.err.println("Error playing sound: " + e.getMessage());
                }
            }

            // Destroy all enemies within the wave’s radius
            List<Enemy> enemiesToRemove = new ArrayList<>(startGame.getConsole().getEnemies());
            for (Enemy enemy : enemiesToRemove) {
                startGame.getConsole().removeEnemy(enemy, true); // Remove enemy and count as defeated
            }
        }
    }

    // Helper method to gradually fade out the dark overlay
    private void fadeOutScreenDarken(GRect screenDarken) {
        new Thread(() -> {
            try {
                for (int alpha = 150; alpha > 0; alpha -= 5) { // Gradual fade-out
                    Thread.sleep(30);
                    screenDarken.setColor(new Color(0, 0, 0, alpha));
                }
                gameApp.remove(screenDarken); // Remove the overlay completely
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Helper method to clamp color values to the valid range of 0–255
    private int clampColorValue(int value) {
        return Math.max(0, Math.min(255, value));
    }



    public void updateCooldowns() {
        // No active updates needed for cooldowns
    }

    public long getHazmatCooldown() {
        return Math.max(0, HAZMAT_COOLDOWN - (System.currentTimeMillis() - lastHazmatUse));
    }

    public long getFlySwatCooldown() {
        return Math.max(0, FLY_SWAT_COOLDOWN - (System.currentTimeMillis() - lastFlySwatUse));
    }

    public long getBugRepellentCooldown() {
        return Math.max(0, BUG_REPELLENT_COOLDOWN - (System.currentTimeMillis() - lastBugRepellentUse));
    }
}

