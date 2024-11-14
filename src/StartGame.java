import acm.graphics.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartGame {
    private GameApp gameApp;
    private GOval playerCircle; 
    private GButton pauseButton; 
    private final int STEP_SIZE = 10; 

    public StartGame(GameApp gameApp) {
        this.gameApp = gameApp;
        initGame();
    }

    private void initGame() {
        gameApp.removeAll(); 

        playerCircle = new GOval(gameApp.getWidth() / 2 - 25, gameApp.getHeight() / 2 - 25, 50, 50);
        playerCircle.setFilled(true);
        playerCircle.setColor(Color.BLUE);
        gameApp.add(playerCircle);

        pauseButton = new GButton("Pause", (int) (gameApp.getWidth() - 100), 20, 80, 30, Color.BLACK, Color.WHITE);
        pauseButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showPauseScreen();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }

    public void show() {
        gameApp.add(playerCircle); 
        gameApp.add(pauseButton.getRect());
        gameApp.add(pauseButton.getMessage());
    }

    public void hide() {
        gameApp.remove(playerCircle);
        gameApp.remove(pauseButton.getRect());
        gameApp.remove(pauseButton.getMessage());
    }

    // Handles key press events for movement
    public void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W: // Move up
                playerCircle.move(0, -STEP_SIZE);
                break;
            case KeyEvent.VK_A: // Move left
                playerCircle.move(-STEP_SIZE, 0);
                break;
            case KeyEvent.VK_S: // Move down
                playerCircle.move(0, STEP_SIZE);
                break;
            case KeyEvent.VK_D: // Move right
                playerCircle.move(STEP_SIZE, 0);
                break;
        }
    }
}

