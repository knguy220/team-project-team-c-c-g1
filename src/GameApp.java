import acm.program.GraphicsProgram;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameApp extends GraphicsProgram {
    private StartGame startGame;
    private StartScreen2 startScreen;
    private SettingsScreen settingsScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;
    private Sound backgroundMusic;
    private Sound clickSound;
    private Sound pauseSound;
    private Sound gunSound;


    // Power-up sounds
    private Sound hazmatSound;
    private Sound flySwatSound;
    private Sound bugRepellentSound;

    public enum GameState { ACTIVE, PAUSED, IN_MENU, GAME_OVER }

    private GameState gameState = GameState.IN_MENU;

    @Override
    public void init() {
        // Initialize the screens and other game elements
        startScreen = new StartScreen2(this);
        settingsScreen = new SettingsScreen(this);
        pauseScreen = new PauseScreen(this);

        // Set up the game screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        // Initialize the background music
        try {
            backgroundMusic = new Sound("media/theme2.wav");

            // Ensure the background music starts looping
            if (backgroundMusic != null) {
                backgroundMusic.loop();
            }

            // Additional sound initializations
            gunSound = new Sound("media/Laser Gun - Sound Effect for editing_0.wav");

         //Initialize power-up sounds
          hazmatSound = new Sound("media/hazmat.wav");
          flySwatSound = new Sound("media/flyswat.wav");
          bugRepellentSound = new Sound("media/bugrepellent.wav");

        } catch (RuntimeException e) {
            System.err.println("There is an error loading sounds: " + e.getMessage());
        } 

        // Show the start screen and add listeners for mouse and keyboard inputs
        showStartScreen();
        addMouseListeners();
        addKeyListeners();
    }

    public StartGame getStartGame() {
        return startGame;
    }

    public void showStartScreen() {
        if (startGame != null) {
            startGame.hide();
            startGame.pauseGameLoop();
        }
        gameState = GameState.IN_MENU;
        startScreen.show();
    }

    public void showSettingsScreen(boolean openedFromPause) {
        if (openedFromPause) {
            pauseScreen.hide();
        } else {
            if (startGame != null) {
                startGame.hide();
                startGame.pauseGameLoop();
            }
            startScreen.hide();
        }
        gameState = GameState.IN_MENU;
        settingsScreen.show(openedFromPause);
    }

    public void startGame() {
        removeAll(); // Clear all elements from the current screen
        if (startGame != null) {
            startGame.pauseGameLoop(); // Stop any ongoing game loop
            startGame = null; // Reset the StartGame instance
        }
        startGame = new StartGame(this); // Create a fresh instance of StartGame
        startGame.show(); // Show the game screen
        gameState = GameState.ACTIVE; // Set the game state to active
    }

    public void showPauseScreen() {
        if (startGame != null) {
            startGame.hide();
            startGame.pauseGameLoop();
        }
        gameState = GameState.PAUSED;
        pauseScreen.show();
    }

    public void showGameOverScreen(int playerScore) {
        removeAll(); // Clear all elements from the screen
        gameOverScreen = new GameOverScreen(this, startScreen.getUsername()); // Reinitialize GameOverScreen
        gameOverScreen.show(playerScore); // Display the Game Over screen
        gameState = GameState.GAME_OVER;
    }

    public void resumeGame() {
        if (startGame != null) {
            pauseScreen.hide();
            startGame.show();
            startGame.resumeGameLoop();
        }
        gameState = GameState.ACTIVE;
    }

    public void quitToStartScreen() {
        if (startGame != null) {
            startGame.hide();
            startGame.pauseGameLoop();
        }
        showStartScreen();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getPlayer() {
        if (startGame != null) {
            return startGame.getPlayer();
        }
        return null;
    }

    public void updateVolume() {
        // Get the current volume levels from the Settings class (scaled to a 0-1 range)
        float musicVolume = Settings.getMusicVolume() / 100.0f;  // Assuming volume is between 0 and 100
        float audioVolume = Settings.getAudioVolume() / 100.0f;  // Assuming volume is between 0 and 100

        // Apply the volume to your sound objects
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(musicVolume);  // Adjusting the background music volume
        }

        if (clickSound != null) {
            clickSound.setVolume(audioVolume);  // Adjusting the audio effects volume
        }

        if (pauseSound != null) {
            pauseSound.setVolume(audioVolume);  // Adjusting the pause sound volume
        }

        if (gunSound != null) {
            gunSound.setVolume(audioVolume);  // Adjusting the gun effects volume
        }

        // Power-up sound volumes
        if (hazmatSound != null) {
            hazmatSound.setVolume(audioVolume);
        }
        if (flySwatSound != null) {
            flySwatSound.setVolume(audioVolume);
        }
        if (bugRepellentSound != null) {
            bugRepellentSound.setVolume(audioVolume);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameState == GameState.PAUSED) {
            if (pauseScreen.isResumeButtonClicked(e.getX(), e.getY())) {
                resumeGame();
            } else if (pauseScreen.isQuitButtonClicked(e.getX(), e.getY())) {
                quitToStartScreen();
            }
        } else if (gameState == GameState.GAME_OVER) {
            if (gameOverScreen != null) {
                gameOverScreen.handleMouseClick(e); // Handle clicks on Game Over screen
            }
        } else if (gameState == GameState.IN_MENU) {
            if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
                settingsScreen.applySettings();
            } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
                settingsScreen.backToPrevious(); // Correct Back button behavior
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gameState == GameState.IN_MENU) {
            settingsScreen.handleSliderDrag(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameState == GameState.IN_MENU) {
            settingsScreen.handleMouseReleased();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (startGame != null && gameState == GameState.ACTIVE) {
            startGame.handleKeyPress(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (startGame != null && gameState == GameState.ACTIVE) {
            startGame.handleKeyRelease(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (startGame != null && gameState == GameState.ACTIVE) {
            startGame.updateAiming(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (startGame != null && gameState == GameState.ACTIVE) {
            startGame.handleShooting(e);
        }
    }

    public void playGunSound() {
        if (gunSound != null) {
            gunSound.play(); // Play the gun sound reliably
        }
    }


    public Sound getHazmatSound() {
        return hazmatSound;
    }

    public Sound getFlySwatSound() {
        return flySwatSound;
    }

    public Sound getBugRepellentSound() {
        return bugRepellentSound;
    }

    public static void main(String[] args) {
        new GameApp().start();
    }

    @Override
    public void run() {
        // Empty implementation for compatibility
    }
}



