import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 


public class StartGame {
    private GameApp gameApp;
    //private GImage backgroundImage; // Commented out the background image
    private Map gameMap;
    private GButton pauseButton;
    private final int GUN_LENGTH = 40;
    private Player player;
    private List<Bullet> bullets;
    private static final int MAX_BULLETS = 20;
    private Console console;
    private Timer gameLoopTimer;
    
    private int score = 0; // Player's score
    private GLabel scoreLabel; // Score label for display

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        
        int screenWidth = (int) gameApp.getWidth();
        int screenHeight = (int) gameApp.getWidth();
        
        this.gameMap = new Map(screenWidth, screenHeight);
        
        this.bullets = new ArrayList<>();

        // Calculate the center of the screen
        int startX = (int) (gameApp.getWidth() / 2 - Player.PLAYER_SIZE / 2);
        int startY = (int) (gameApp.getHeight() / 2 - Player.PLAYER_SIZE / 2);

        this.player = new Player(gameApp, startX, startY, GUN_LENGTH, gameMap);
        this.console = new Console(gameApp);

        initializeGame();

        // Game loop timer to update gameplay elements
        gameLoopTimer = new Timer(30, e -> update());
        gameLoopTimer.start();
    }

    private void initializeGame() {
        gameApp.removeAll();

        // Initialize player
        player.initialize();
        
        gameMap.render(gameApp);
        
        // Initialize score label
        scoreLabel = new GLabel("Score: 0");
        scoreLabel.setFont("Arial-Bold-20");
        scoreLabel.setColor(Color.BLACK); // Black color for better visibility
        scoreLabel.setLocation(10, 50); // Position south of the health bar
        gameApp.add(scoreLabel);

        // Initialize pause button
        pauseButton = new GButton("Pause", (int) (gameApp.getWidth() - 100), 20, 80, 30, Color.BLACK, Color.WHITE);
        pauseButton.addActionListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showPauseScreen();
            }
        });
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }


    public void show() {
        // if (backgroundImage != null) gameApp.add(backgroundImage);
        if (pauseButton != null) {
            gameApp.add(pauseButton.getRect());
            gameApp.add(pauseButton.getMessage());
        }

        // Show player
        player.show();
        player.resetMovement();

        // Add score label
        gameApp.add(scoreLabel);

        // Add existing bullets to the screen
        for (Bullet bullet : bullets) {
            gameApp.add(bullet.getBulletShape());
        }

        // Show all enemies
        console.showAllEnemies();

        // Resume timers
        resumeGameLoop();
        console.resumeWaveTimer();
    }

    public void hide() {
        // if (backgroundImage != null) gameApp.remove(backgroundImage);
        if (pauseButton != null) {
            gameApp.remove(pauseButton.getRect());
            gameApp.remove(pauseButton.getMessage());
        }

        // Hide player and bullets
        player.hide();
        player.resetMovement();
        for (Bullet bullet : bullets) {
            gameApp.remove(bullet.getBulletShape());
        }

        // Remove score label
        gameApp.remove(scoreLabel);

        // Hide enemies and pause timers
        console.hideAllEnemies();
        pauseGameLoop();
        console.pauseWaveTimer();
    }

    public void updateScore(int points) {
        score += points;
        scoreLabel.setLabel("Score: " + score);

        // Dynamic animation for the score label
        new Thread(() -> {
            try {
                // Initial hover and scale-up effect
                for (int i = 0; i < 10; i++) {
                    scoreLabel.setFont("Arial-Bold-" + (20 + i)); // Gradually increase font size
                    Thread.sleep(15); // Smooth transition
                }

                // Hover back to original position and font size
                for (int i = 0; i < 10; i++) {
                    scoreLabel.setFont("Arial-Bold-" + (30 - i)); // Gradually decrease font size
                    Thread.sleep(15); // Smooth transition
                }

                // Ensure it resets back to the default
                scoreLabel.setFont("Arial-Bold-20");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void pauseGameLoop() {
        if (gameLoopTimer != null) {
            gameLoopTimer.stop();
        }
    }

    public void resumeGameLoop() {
        if (gameLoopTimer != null && !gameLoopTimer.isRunning()) {
            gameLoopTimer.start();
        }
    }

    public void handleKeyPress(KeyEvent e) {
        player.handleKeyPress(e);

        // Trigger GameOverScreen with 'Q'
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            triggerGameOver();
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        player.handleKeyRelease(e);
    }

    public void updateAiming(MouseEvent e) {
        player.updateAiming(e);
    }

//    public void handleShooting(MouseEvent e) {
//        if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
//            double playerCenterX = player.getCenterX();
//            double playerCenterY = player.getCenterY();
//            double mouseX = e.getX();
//            double mouseY = e.getY();
//
//            // Calculate direction of bullet
//            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
//            double directionX = Math.cos(angle);
//            double directionY = Math.sin(angle);
//
//            Bullet bullet = new Bullet(gameApp, this, playerCenterX, playerCenterY, directionX, directionY);
//            bullets.add(bullet);
//        }
//    } 
    
    public void handleShooting(MouseEvent e) {
        if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
            double playerCenterX = player.getCenterX();
            double playerCenterY = player.getCenterY();
            double mouseX = e.getX();
            double mouseY = e.getY();

            // Calculate direction of bullet
            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
            double directionX = Math.cos(angle);
            double directionY = Math.sin(angle);

            Bullet bullet = new Bullet(gameApp, this, playerCenterX, playerCenterY, directionX, directionY);
            bullets.add(bullet);

            // Play gun sound
            gameApp.playGunSound();
        }
    }


    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        gameApp.remove(bullet.getBulletShape());
    }

    private void update() {
        // Update player movement
        player.updateMovement();

        // Update enemies to follow the player
        console.updateEnemies(player);

        // Update bullets
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.updatePosition();
        }

        // Check for collisions
        checkCollisions();

        // Check for player and enemy collisions
        checkPlayerEnemyCollisions();
        
        checkingPlayerAndMedKit();

        // Trigger Game Over when health reaches 0
        if (!player.isAlive()) {
            triggerGameOver();
        }
    } 
    
    private void checkPlayerEnemyCollisions() {
        for (Enemy enemy : console.getEnemies()) {
            if (isPlayerCollidingWithEnemy(player, enemy)) {
                player.updateHealth(enemy.getDamage(), 0); // Decrease health by 10
                console.removeEnemy(enemy,false); // Optionally remove the enemy upon collision
                break;
            }
        }
    }
    private void checkingPlayerAndMedKit() {
    	if (player.getPressedE() && console.checkPlayerMedKit(player)) {
    		console.healPlayer(player);
    		console.removeMedKit();
    		
    	}
    }

    private boolean isPlayerCollidingWithEnemy(Player player, Enemy enemy) {
        double playerX = player.getCenterX();
        double playerY = player.getCenterY();
        double enemyX = enemy.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
        double enemyY = enemy.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

        double distance = Math.hypot(playerX - enemyX, playerY - enemyY);
        return distance < (Player.PLAYER_SIZE / 2 + Enemy.getEnemySize() / 2);
    }

    private void triggerGameOver() {
        pauseGameLoop();
        console.pauseWaveTimer();
        int finalScore = console.getEnemiesDefeated(); // Get the player's score
        gameApp.showGameOverScreen(finalScore); // Navigate to Game Over screen
    }

    private void checkCollisions() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = console.getEnemies().size() - 1; j >= 0; j--) {
                Enemy enemy = console.getEnemies().get(j);
                if (isBulletCollidingWithEnemy(bullet, enemy)) {
                    //console.removeEnemy(enemy);
                	enemy.updateHealth(10);
                	if (!enemy.isAlive()) {
                		console.removeEnemy(enemy,true);
                		updateScore(1); // Increment score by 1 for each enemy killed
                	}
                    bullet.createImpactAnimation();
                    bullet.destroy();
                    break;
                }
            }
        }
    } 
    


    private boolean isBulletCollidingWithEnemy(Bullet bullet, Enemy enemy) {
        double bulletX = bullet.getBulletShape().getX() + Bullet.getBulletSize() / 2;
        double bulletY = bullet.getBulletShape().getY() + Bullet.getBulletSize() / 2;
        double enemyX = enemy.getEnemyShape().getX() + Enemy.getEnemySize() / 2;
        double enemyY = enemy.getEnemyShape().getY() + Enemy.getEnemySize() / 2;

        double distance = Math.hypot(bulletX - enemyX, bulletY - enemyY);
        return distance < Bullet.getBulletSize() / 2 + Enemy.getEnemySize() / 2;
    }

    public Player getPlayer() {
        return player;
    }
}

