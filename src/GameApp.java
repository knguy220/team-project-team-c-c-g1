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
    private boolean fromPauseScreen = false; 

    public void init() {
        startScreen = new StartScreen2(this);
        settingsScreen = new SettingsScreen(this);
        pauseScreen = new PauseScreen(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        showStartScreen();
        addMouseListeners();
        addKeyListeners();
    }

    public void showStartScreen() {
        if (startGame != null) startGame.hide(); 
        startScreen.show();
    }

    public void showSettingsScreen(boolean openedFromPause) {
    	
        fromPauseScreen = openedFromPause;

        if (fromPauseScreen) {
            pauseScreen.hide(); 
        } else {
            if (startGame != null) startGame.hide();
            startScreen.hide();
        }
        
        settingsScreen.show();
    }

    public void startGame() {
        removeAll();
        if (startGame == null) { 
            startGame = new StartGame(this);
        } else {
            startGame.show(); 
        }
        isPaused = false;
    }

    public void showPauseScreen() {
        if (startGame != null) startGame.hide(); 
        pauseScreen.show();
        isPaused = true;
    }

    public void resumeGame() {
        if (startGame != null) {
            pauseScreen.hide(); 
            startGame.show(); 
        }
        isPaused = false;
    }

    public void quitToStartScreen() {
        if (startGame != null) startGame.hide(); 
        showStartScreen(); 
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

    	if (isPaused && pauseScreen.isResumeButtonClicked(e.getX(), e.getY())) {
            resumeGame();
        } else if (isPaused && pauseScreen.isSettingsButtonClicked(e.getX(), e.getY())) {
            showSettingsScreen(true); 
        } else if (isPaused && pauseScreen.isQuitButtonClicked(e.getX(), e.getY())) {
            quitToStartScreen();
        } else if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
            settingsScreen.applySettings();
        } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
            settingsScreen.hide();
            if (fromPauseScreen) {
                resumeGame(); 
            } else {
                showStartScreen(); 
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


