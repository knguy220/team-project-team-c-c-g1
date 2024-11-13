import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartScreen2 {
    private GameApp gameApp; // Reference to the main GameApp class
    private GLabel instructionsLabel; // Label for displaying game instructions

    /**
     * Constructor initializes StartScreen2 with a reference to the main GameApp.
     *
     * @param gameApp The main GameApp instance to enable screen transitions
     */ 
    public StartScreen2(GameApp gameApp) {
        this.gameApp = gameApp;
    }
    
    /**
     * Displays the start screen, adding UI elements like title, settings, instructions,
     * and start buttons. Clears any existing UI elements before adding new ones.
     */ 
    public void show() {
        gameApp.removeAll(); // Clear existing UI elements before adding new ones
        
        // Title label for the game
        GLabel titleLabel = new GLabel("Commander Dez", 585, 100);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 60));
        titleLabel.setColor(Color.BLUE);
        gameApp.add(titleLabel);

        // Settings button - Opens the settings screen when clicked
        GButton settingsButton = new GButton("Settings", 730, 240, 100, 40, Color.BLACK, Color.WHITE);
        settingsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showSettingsScreen(); // Transition to settings screen
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(settingsButton.getRect()); // Add the button background to GameApp
        gameApp.add(settingsButton.getMessage()); // Add the button label to GameApp

        // Instructions button - Displays game instructions when clicked
        GButton instructionsButton = new GButton("Instructions", 730, 400, 100, 40, Color.BLACK, Color.WHITE);
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
        GButton startButton = new GButton("Start Game", 730, 600, 100, 40, Color.BLACK, Color.WHITE);
        startButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startGame(); // Call GameApp's startGame method to initiate the game
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(startButton.getRect());
        gameApp.add(startButton.getMessage());
    }

    /**
     * Hides the start screen by removing all elements.
     */
    public void hide() {
        gameApp.removeAll();
    }

    /**
     * Displays game instructions. Clears any previous instructions before displaying
     * the updated text.
     */
    private void showInstructions() {
        if (instructionsLabel != null) {
            gameApp.remove(instructionsLabel); // Remove old instructions if they exist
        }
        // Instructional text describing game controls and goals
        instructionsLabel = new GLabel(
            "Survive as long as possible. Use W, A, S, D to move. " +
            "Use the mouse to aim and shoot. Use E to pick up items.", 100, 385);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsLabel.setColor(Color.BLACK);
        gameApp.add(instructionsLabel);
    }

    /**
     * Initiates the game by calling GameApp's startGame method.
     */
    private void startGame() {
        gameApp.startGame(); // Start the game in GameApp
    }
}
