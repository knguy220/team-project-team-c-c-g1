import acm.graphics.*;
import acm.graphics.GImage;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import acm.program.GraphicsProgram;


public class StartScreen2 {
    private GameApp gameApp; // Reference to the main GameApp class
    private GLabel instructionsLabel;
    private GButton startButton;// Label for displaying game instructions
    private JTextField usernameField; // USERNAME
    private GButton instructionsButton;
    private GImage background;
    
    public static final int WINDOW_HEIGHT = 700;
	public static final int WINDOW_WIDTH = 700; 
	
    /**
     * Constructor initializes StartScreen2 with a reference to the main GameApp.
     *
     * @param gameApp The main GameApp instance to enable screen transitions
     */ 
    public StartScreen2(GameApp gameApp) {
        this.gameApp = gameApp;
        GButton startButton = new GButton("Start", 730, 600, 100, 40, Color.BLACK, Color.WHITE);
    }
    
    /**
     * Displays the start screen, adding UI elements like title, settings, instructions,
     * and start buttons. Clears any existing UI elements before adding new ones.
     */ 
    
    public void show() {
        gameApp.removeAll(); // Clear existing UI elements before adding new ones
        
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        background = new GImage("space.jpg");
        background.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
        add(background, 0, 0); 
        
        
     //Title label for the game
        GLabel titleLabel = new GLabel("Commander Dez", 585, 100);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 60));
        titleLabel.setColor(Color.blue);
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
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

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
                startGame(); // Initiate the game
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(startButton.getRect());
        gameApp.add(startButton.getMessage());
    }

    private void setSize(int windowWidth, int windowHeight) {
		// TODO Auto-generated method stub
		
	}

	private void add(GImage background2, int i, int j) {
		// TODO Auto-generated method stub
		
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
     * Initiates the game by clearing the start screen and displaying a message.
     * This would be expanded to start the main game loop in a full implementation.
     */
 
   private void startGame() {
       gameApp.removeAll();
       // Display a welcome message indicating the game is starting
       GLabel startLabel = new GLabel("Game Starting...", 650, 300);
       startLabel.setFont(new Font("Arial", Font.BOLD, 32));
        startLabel.setColor(Color.RED);
       gameApp.add(startLabel);
   }
 }
