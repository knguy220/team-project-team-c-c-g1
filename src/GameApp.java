import acm.program.GraphicsProgram;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameApp extends GraphicsProgram {
    private StartScreen2 startScreen;
    private SettingsScreen settingsScreen;
    private StartGame startGame;
    private PauseScreen pauseScreen;
    private boolean isPaused = false;
    private boolean fromPauseScreen = false; // Track if settings was opened from pause

    public void init() {
        startScreen = new StartScreen2(this);
        settingsScreen = new SettingsScreen(this);
        pauseScreen = new PauseScreen(this);

        // Set the window to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        showStartScreen();
        addMouseListeners();
        addKeyListeners();
    }

    public void showStartScreen() {
        if (startGame != null) startGame.hide(); // Hide StartGame if switching back to start screen
        startScreen.show();
    }

    public void showSettingsScreen(boolean openedFromPause) {
    	
        fromPauseScreen = openedFromPause;

        if (fromPauseScreen) {
            pauseScreen.hide(); // Hide pause screen if coming from pause
        } else {
            if (startGame != null) startGame.hide();
            startScreen.hide();
        }
        
        settingsScreen.show();
    }

    public void startGame() {
        removeAll();
        if (startGame == null) { // Only create StartGame once
            startGame = new StartGame(this);
        } else {
            startGame.show(); // Show the existing StartGame instance without resetting
        }
        isPaused = false;
    }

    public void showPauseScreen() {
        if (startGame != null) startGame.hide(); // Hide StartGame when pausing
        pauseScreen.show();
        isPaused = true;
    }

    public void resumeGame() {
        if (startGame != null) {
            pauseScreen.hide(); // Hide pause screen
            startGame.show(); // Show StartGame elements when resuming
        }
        isPaused = false;
    }

    public void quitToStartScreen() {
        if (startGame != null) startGame.hide(); // Hide the game elements
        showStartScreen(); // Go back to main StartScreen
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (startGame != null && !isPaused) {
            startGame.handleKeyPress(e);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle settings button behavior based on the active screen state
        if (isPaused && pauseScreen.isResumeButtonClicked(e.getX(), e.getY())) {
            resumeGame();
        } else if (isPaused && pauseScreen.isSettingsButtonClicked(e.getX(), e.getY())) {
            showSettingsScreen(true); // Settings from PauseScreen
        } else if (isPaused && pauseScreen.isQuitButtonClicked(e.getX(), e.getY())) {
            quitToStartScreen();
        } else if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
            // Only apply settings without navigation
            settingsScreen.applySettings();
        } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
            // Go back based on where settings were opened from
            settingsScreen.hide();
            if (fromPauseScreen) {
                resumeGame(); // Return to StartGame if accessed from PauseScreen
            } else {
                showStartScreen(); // Return to StartScreen if accessed from StartScreen2
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        settingsScreen.handleSliderDrag(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        settingsScreen.handleMouseReleased();
    }

    public static void main(String[] args) {
        new GameApp().start();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


