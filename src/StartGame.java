import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class StartGame {
	private GameApp gameApp;
	private GImage backgroundImage;
	private GButton pauseButton;
	private final int GUN_LENGTH = 40;
	private Player player;
	private List<Bullet> bullets;
	private static final int MAX_BULLETS = 20;
	private Console console;
	private Timer gameLoopTimer;
	private Timer waveTimer;

	private int waveNumber = 1;
	private boolean isSpawningWave = false;

	public StartGame(GameApp gameApp) {
	    this.gameApp = gameApp;
	    this.bullets = new ArrayList<>();

	    // Calculate the center of the screen
	    int startX = (int) (gameApp.getWidth() / 2 - Player.PLAYER_SIZE / 2);
	    int startY = (int) (gameApp.getHeight() / 2 - Player.PLAYER_SIZE / 2);

	    this.player = new Player(gameApp, startX, startY, GUN_LENGTH);
	    this.console = new Console(gameApp);

	    initializeGame();

	    // Game loop timer to update gameplay elements
	    gameLoopTimer = new Timer(20, e -> update());
	    gameLoopTimer.start();

	    // Wave spawning timer
	    waveTimer = new Timer(10000, e -> startNextWave());
	    waveTimer.start();
	}


	private void initializeGame() {
		gameApp.removeAll();

		// Add background image
		backgroundImage = new GImage("groundhd.png");
		backgroundImage.setSize(gameApp.getWidth(), gameApp.getHeight());
		gameApp.add(backgroundImage);

		// Initialize player
		player.initialize();

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
		if (backgroundImage != null)
			gameApp.add(backgroundImage);
		if (pauseButton != null) {
			gameApp.add(pauseButton.getRect());
			gameApp.add(pauseButton.getMessage());
		}

		// Show player
		player.show();
		player.resetMovement(); // Ensure player movement state is reset on resume

		// Add existing bullets to the screen
		for (Bullet bullet : bullets) {
			gameApp.add(bullet.getBulletShape());
		}

		// Show all enemies
		console.showAllEnemies();

		// Resume timers
		resumeGameLoop();
		resumeWaveTimer();
	}

	public void hide() {
		if (backgroundImage != null)
			gameApp.remove(backgroundImage);
		if (pauseButton != null) {
			gameApp.remove(pauseButton.getRect());
			gameApp.remove(pauseButton.getMessage());
		}

		// Hide player and bullets
		player.hide();
		player.resetMovement(); // Stop player movement when pausing
		for (Bullet bullet : bullets) {
			gameApp.remove(bullet.getBulletShape());
		}

		// Hide enemies and pause timers
		console.hideAllEnemies();
		pauseGameLoop();
		pauseWaveTimer();
	}

	private void startNextWave() {
		if (gameApp.getGameState() != GameApp.GameState.ACTIVE || isSpawningWave) {
			return; // Only spawn waves if the game is active and not already spawning
		}

		isSpawningWave = true;
		int enemyCount = Math.min(waveNumber * 3, 20); // Gradual increase, cap at 20 enemies
		int spawnDelay = 500; // Delay between individual enemy spawns (in ms)

		for (int i = 0; i < enemyCount; i++) {
			int finalI = i;
			Timer spawnTimer = new Timer(spawnDelay * finalI, e -> {
				if (gameApp.getGameState() == GameApp.GameState.ACTIVE && console.getEnemies().size() < 20) {
					console.spawnEnemy();
				}
			});
			spawnTimer.setRepeats(false);
			spawnTimer.start();
		}

		waveNumber++; // Increment wave number for the next wave
		isSpawningWave = false;
	}

	public void pauseWaveTimer() {
		if (waveTimer != null) {
			waveTimer.stop();
		}
	}

	public void resumeWaveTimer() {
		if (waveTimer != null && !waveTimer.isRunning()) {
			waveTimer.start();
		}
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
	}

	public void handleKeyRelease(KeyEvent e) {
		player.handleKeyRelease(e);
	}

	public void updateAiming(MouseEvent e) {
		player.updateAiming(e);
	}

	public void handleShooting(MouseEvent e) {
		if (Bullet.canShoot() && bullets.size() < MAX_BULLETS) {
			double centerX = player.getCenterX();
			double centerY = player.getCenterY();
			double mouseX = e.getX();
			double mouseY = e.getY();

			// Calculate direction of bullet
			double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
			double directionX = Math.cos(angle);
			double directionY = Math.sin(angle);

			Bullet bullet = new Bullet(gameApp, this, centerX, centerY, directionX, directionY);
			bullets.add(bullet);
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
	}

	private void checkCollisions() {
		for (int i = bullets.size() - 1; i >= 0; i--) {
			Bullet bullet = bullets.get(i);
			for (int j = console.getEnemies().size() - 1; j >= 0; j--) {
				Enemy enemy = console.getEnemies().get(j);
				if (isBulletCollidingWithEnemy(bullet, enemy)) {
					console.removeEnemy(enemy);
					removeBullet(bullet);
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
