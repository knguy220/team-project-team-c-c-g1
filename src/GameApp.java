//import acm.program.GraphicsProgram;
//import java.awt.Toolkit;
//import java.awt.Dimension;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//
//public class GameApp extends GraphicsProgram {
//    private StartGame startGame;
//    private StartScreen2 startScreen;
//    private SettingsScreen settingsScreen;
//    private PauseScreen pauseScreen;
//    private GameOverScreen gameOverScreen;
//    private Sound backgroundMusic;
//    private Sound pauseSound;
//    private Sound gameOverSound;
//    private Sound clickSound;
//
//
//    public enum GameState { ACTIVE, PAUSED, IN_MENU, GAME_OVER }
//
//    private GameState gameState = GameState.IN_MENU;
//
//    public void init() {
//        startScreen = new StartScreen2(this);
//        settingsScreen = new SettingsScreen(this);
//        pauseScreen = new PauseScreen(this);
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setSize(screenSize.width, screenSize.height);
//
//        showStartScreen();
//        addMouseListeners();
//        addKeyListeners(); 
//        
//       
//            // Initialize sounds
//            backgroundMusic = new Sound("path/to/background_music.wav");
//            pauseSound = new Sound("path/to/pause_sound.wav");
//            gameOverSound = new Sound("path/to/game_over_sound.wav");
//            clickSound = new Sound("path/to/click_sound.wav");
//
//            // Play background music in a loop
//            backgroundMusic.loop();
//
//
//            // Show start screen and add listeners
//            showStartScreen();
//            addMouseListeners();
//            addKeyListeners();
//        }
//
//
//    public void showStartScreen() {
//        if (startGame != null) {
//            startGame.hide();
//            startGame.pauseGameLoop();
//        }
//        gameState = GameState.IN_MENU;
//        startScreen.show();
//    }
//
//    public void showSettingsScreen(boolean openedFromPause) {
//        if (openedFromPause) {
//            pauseScreen.hide();
//        } else {
//            if (startGame != null) {
//                startGame.hide();
//                startGame.pauseGameLoop();
//            }
//            startScreen.hide();
//        }
//
//        gameState = GameState.IN_MENU;
//        settingsScreen.show(openedFromPause);
//    }
//
//    public void startGame() {
//        removeAll(); // Clear all elements from the current screen
//        if (startGame != null) {
//            startGame.pauseGameLoop(); // Stop any ongoing game loop
//            startGame = null; // Reset the StartGame instance
//        }
//        startGame = new StartGame(this); // Create a fresh instance of StartGame
//        startGame.show(); // Show the game screen
//        gameState = GameState.ACTIVE; // Set the game state to active
//        
//        clickSound.play();
//        backgroundMusic.stop(); 
//    }
//
//    public void showPauseScreen() {
//        if (startGame != null) {
//            startGame.hide();
//            startGame.pauseGameLoop();
//        }
//        gameState = GameState.PAUSED;
//        pauseScreen.show();
//    }
//
//    public void showGameOverScreen(int playerScore) {
//        removeAll(); // Clear all elements from the screen
//        gameOverScreen = new GameOverScreen(this, startScreen.getUsername()); // Reinitialize GameOverScreen
//        gameOverScreen.show(playerScore); // Display the Game Over screen
//        gameState = GameState.GAME_OVER;
//    }
//
//    public void resumeGame() {
//        if (startGame != null) {
//            pauseScreen.hide();
//            startGame.show();
//            startGame.resumeGameLoop();
//        }
//        gameState = GameState.ACTIVE;
//    }
//
//    public void quitToStartScreen() {
//        if (startGame != null) {
//            startGame.hide();
//            startGame.pauseGameLoop();
//        }
//        showStartScreen();
//    }
//
//    public GameState getGameState() {
//        return gameState;
//    }
//
//    public Player getPlayer() {
//        if (startGame != null) {
//            return startGame.getPlayer();
//        }
//        return null;
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        if (gameState == GameState.PAUSED) {
//            if (pauseScreen.isResumeButtonClicked(e.getX(), e.getY())) {
//                resumeGame();
//            } else if (pauseScreen.isSettingsButtonClicked(e.getX(), e.getY())) {
//                showSettingsScreen(true); // Opens settings from pause
//            } else if (pauseScreen.isQuitButtonClicked(e.getX(), e.getY())) {
//                quitToStartScreen();
//            }
//        } else if (gameState == GameState.GAME_OVER) {
//            if (gameOverScreen != null) {
//                gameOverScreen.handleMouseClick(e); // Handle clicks on Game Over screen
//            }
//        } else if (gameState == GameState.IN_MENU) {
//            if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
//                settingsScreen.applySettings();
//            } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
//                settingsScreen.backToPrevious(); // Correct Back button behavior
//            }
//        }
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        if (gameState == GameState.IN_MENU) {
//            settingsScreen.handleSliderDrag(e);
//        }
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        if (gameState == GameState.IN_MENU) {
//            settingsScreen.handleMouseReleased();
//        }
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if (startGame != null && gameState == GameState.ACTIVE) {
//            startGame.handleKeyPress(e);
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        if (startGame != null && gameState == GameState.ACTIVE) {
//            startGame.handleKeyRelease(e);
//        }
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//        if (startGame != null && gameState == GameState.ACTIVE) {
//            startGame.updateAiming(e);
//        }
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        if (startGame != null && gameState == GameState.ACTIVE) {
//            startGame.handleShooting(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        new GameApp().start();
//    }
//
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//}
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
    private Sound gameOverSound;
    
    public enum GameState { ACTIVE, PAUSED, IN_MENU, GAME_OVER }

    private GameState gameState = GameState.IN_MENU;

    
//    public void init() {
//        startScreen = new StartScreen2(this);
//        settingsScreen = new SettingsScreen(this);
//        pauseScreen = new PauseScreen(this);
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setSize(screenSize.width, screenSize.height);
//
//        // Initialize sounds with paths relative to the project directory
//        try {
//            backgroundMusic = new Sound("media/kanye.wav");
//            
//
//            // Loop background music
//            backgroundMusic.loop();
//        } catch (RuntimeException e) {
//            System.err.println("Error loading sounds: " + e.getMessage());
//        }
//
//        // Show start screen and add listeners
//        showStartScreen();
//        addMouseListeners();
//        addKeyListeners();
//    } 
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
            // Initialize the background music from the media folder
            backgroundMusic = new Sound("media/kanye.wav");

            // Ensure the background music starts looping
            if (backgroundMusic != null) {
                backgroundMusic.loop();  // Start looping the background music
            }

            // Additional sound initializations (if needed for other game sounds)
            // Example: pauseSound = new Sound("Audio/pause_sound.wav");
            // Example: gameOverSound = new Sound("Audio/game_over_sound.wav");
            // Example: clickSound = new Sound("Audio/click_sound.wav");

        } catch (RuntimeException e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }

        // Show the start screen and add listeners for mouse and keyboard inputs
        showStartScreen();
        addMouseListeners();
        addKeyListeners();
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

        if (gameOverSound != null) {
            gameOverSound.setVolume(audioVolume);  // Adjusting the game over sound volume
        }

        // Add more sound objects if needed
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameState == GameState.PAUSED) {
            if (pauseScreen.isResumeButtonClicked(e.getX(), e.getY())) {
                resumeGame();
            } else if (pauseScreen.isSettingsButtonClicked(e.getX(), e.getY())) {
                showSettingsScreen(true); // Opens settings from pause
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

    public static void main(String[] args) {
        new GameApp().start();
    }

    @Override
    public void run() {
        // Empty implementation for compatibility
    }
}



