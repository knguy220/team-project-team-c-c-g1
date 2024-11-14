import acm.program.GraphicsProgram;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;


public class GameApp extends GraphicsProgram implements KeyListener {
    private StartScreen2 startScreen;
    private SettingsScreen settingsScreen;
    private StartGame startGame;
    private PauseScreen pauseScreen;

    public void init() {
        startScreen = new StartScreen2(this);
        settingsScreen = new SettingsScreen(this);
        pauseScreen = new PauseScreen(this);
        showStartScreen();

        addMouseListeners(); 
        addKeyListeners();
    }

    public void showStartScreen() {
        startScreen.show();
    }

    public void showSettingsScreen() {
        startScreen.hide();
        settingsScreen.show();
    }

    public void startGame() {
        removeAll();
        startGame = new StartGame(this);
    }

    public void showPauseScreen() {
        pauseScreen.show();
    }

    public void resumeGame() {
        startGame = new StartGame(this); // Re-initialize StartGame to resume
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (startGame != null) {
            startGame.handleKeyPress(e);
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (settingsScreen.isDraggingSlider()) return;

        if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
            settingsScreen.applySettings();
        } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
            settingsScreen.goBack();
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

