import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartScreen2 {
    private GameApp gameApp; // Reference to the main GameApp class
    private GLabel instructionsLabel; // Label for displaying game instructions
    private GButton startButton;
    private GButton settingsButton;
    private GButton instructionsButton;

    public StartScreen2(GameApp gameApp) {
        this.gameApp = gameApp;
    }
    
    public void show() {
        gameApp.removeAll(); // Clear existing UI elements before adding new ones

        // Title label for the game
        GLabel titleLabel = new GLabel("Commander Dez", 585, 100);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 60));
        titleLabel.setColor(Color.GREEN);
        gameApp.add(titleLabel);

        // Settings button - Opens the settings screen when clicked
        settingsButton = new GButton("Settings", 730, 240, 100, 40, Color.BLACK, Color.WHITE);
        settingsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showSettingsScreen(false); // Open settings from StartScreen2 (not from pause)
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

        // Instructions button - Displays game instructions when clicked
        instructionsButton = new GButton("Instructions", 730, 400, 100, 40, Color.BLACK, Color.WHITE);
        instructionsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showInstructions(); // Display instructions
            } 
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(instructionsButton.getRect());
        gameApp.add(instructionsButton.getMessage());

        // Start button - Begins the game when clicked
        startButton = new GButton("Start Game", 730, 600, 100, 40, Color.BLACK, Color.WHITE);
        startButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.startGame(); // Start the game
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(startButton.getRect());
        gameApp.add(startButton.getMessage());
    }

    public void hide() {
        gameApp.removeAll();
    }

    private void showInstructions() {
        if (instructionsLabel != null) {
            gameApp.remove(instructionsLabel); // Remove old instructions if they exist
        }
        // Instructional text describing game controls and goals
        instructionsLabel = new GLabel(
            "Survive as long as possible. Use W, A, S, D to move. " +
            "Use the mouse to aim and shoot. Use E to pick up items.", 400, 385);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsLabel.setColor(Color.BLACK);
        gameApp.add(instructionsLabel);
    }
}

