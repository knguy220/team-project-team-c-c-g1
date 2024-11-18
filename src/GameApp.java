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

    public enum GameState { ACTIVE, PAUSED, IN_MENU }

    private GameState gameState = GameState.IN_MENU;

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
        removeAll();
        if (startGame == null) {
            startGame = new StartGame(this);
        } else {
            startGame.resumeGameLoop();
            startGame.show();
        }
        gameState = GameState.ACTIVE;
    }

    public void showPauseScreen() {
        if (startGame != null) {
            startGame.hide();
            startGame.pauseGameLoop();
        }
        gameState = GameState.PAUSED;
        pauseScreen.show();
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
        } else if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
            settingsScreen.applySettings();
        } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
            settingsScreen.backToPrevious(); // Correct Back button behavior
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
		// TODO Auto-generated method stub
		
	}
}




