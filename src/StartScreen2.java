//import acm.graphics.*;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import javax.swing.JTextField;
//
//public class StartScreen2 {
//    private GameApp gameApp; 
//    private GLabel instructionsLabel; 
//    private GButton startButton;
//    private GButton settingsButton;
//    private GButton instructionsButton;
//    private GImage backgroundImage;
//    private GImage gameTitleImage;
//    private JTextField usernameField;
//    private GLabel usernameLabel;
//
//    public StartScreen2(GameApp gameApp) {
//        this.gameApp = gameApp;
//    }
//    
//    public void show() {
//        gameApp.removeAll();
//        
//        backgroundImage = new GImage("clouds.png");
//        backgroundImage.setSize(gameApp.getWidth(), gameApp.getHeight());
//        gameApp.add(backgroundImage);
//        
//        gameTitleImage = new GImage("commanderfont.png");
//        gameTitleImage.setLocation((gameApp.getWidth() - gameTitleImage.getWidth()) / 2, 50);
//        gameApp.add(gameTitleImage);
//
//        // Settings button - Opens the settings screen when clicked
//        settingsButton = new GButton("Settings", 730, 240, 100, 40, Color.BLACK, Color.WHITE);
//        settingsButton.addActionListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                gameApp.showSettingsScreen(false);
//            }
//            @Override public void mousePressed(MouseEvent e) {}
//            @Override public void mouseReleased(MouseEvent e) {}
//            @Override public void mouseEntered(MouseEvent e) {}
//            @Override public void mouseExited(MouseEvent e) {}
//        });
//        gameApp.add(settingsButton.getRect());
//        gameApp.add(settingsButton.getMessage());
//
//        instructionsButton = new GButton("Instructions", 730, 400, 100, 40, Color.BLACK, Color.WHITE);
//        instructionsButton.addActionListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                showInstructions(); 
//            } 
//            @Override public void mousePressed(MouseEvent e) {}
//            @Override public void mouseReleased(MouseEvent e) {}
//            @Override public void mouseEntered(MouseEvent e) {}
//            @Override public void mouseExited(MouseEvent e) {}
//        });
//        gameApp.add(instructionsButton.getRect());
//        gameApp.add(instructionsButton.getMessage());
//        
//        
//        
//
//        // Start button - Begins the game when clicked
//        startButton = new GButton("Start Game", 730, 600, 100, 40, Color.BLACK, Color.WHITE);
//        startButton.addActionListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                gameApp.startGame(); 
//            }
//            @Override public void mousePressed(MouseEvent e) {}
//            @Override public void mouseReleased(MouseEvent e) {}
//            @Override public void mouseEntered(MouseEvent e) {}
//            @Override public void mouseExited(MouseEvent e) {}
//        });
//        gameApp.add(startButton.getRect());
//        gameApp.add(startButton.getMessage());
//    
//    
//}
//
//    public void hide() {
//        gameApp.removeAll();
//    }
//
//    private void showInstructions() {
//        if (instructionsLabel != null) {
//            gameApp.remove(instructionsLabel); 
//        }
//        
//        // Instructional text describing game controls and goals
//        instructionsLabel = new GLabel(
//            "Survive as long as possible. Use W, A, S, D to move. " +
//            "Use the mouse to aim and shoot. Use E to pick up items.", 400, 385);
//        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//        instructionsLabel.setColor(Color.WHITE);
//        gameApp.add(instructionsLabel);
//    }
//} 
import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

public class StartScreen2 {
    private GameApp gameApp;
    private GLabel instructionsLabel;
    private GButton startButton;
    private GButton settingsButton;
    private GButton instructionsButton;
    private GImage backgroundImage;
    private GImage gameTitleImage;
    private String username; // To store the username

    public StartScreen2(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public void show() {
        gameApp.removeAll();

        backgroundImage = new GImage("clouds.png");
        backgroundImage.setSize(gameApp.getWidth(), gameApp.getHeight());
        gameApp.add(backgroundImage);

        gameTitleImage = new GImage("commanderfont.png");
        gameTitleImage.setLocation((gameApp.getWidth() - gameTitleImage.getWidth()) / 2, 50);
        gameApp.add(gameTitleImage);

        // Settings button - Opens the settings screen when clicked
        settingsButton = new GButton("Settings", 730, 240, 100, 40, Color.BLACK, Color.WHITE);
        settingsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameApp.showSettingsScreen(false);
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        gameApp.add(settingsButton.getRect());
        gameApp.add(settingsButton.getMessage());

        instructionsButton = new GButton("Instructions", 730, 400, 100, 40, Color.BLACK, Color.WHITE);
        instructionsButton.addActionListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showInstructions();
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
                requestUsername();
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
            gameApp.remove(instructionsLabel);
        }

        instructionsLabel = new GLabel(
            "Survive as long as possible. Use W, A, S, D to move. " +
            "Use the mouse to aim and shoot. Use E to pick up items.", 400, 385);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsLabel.setColor(Color.WHITE);
        gameApp.add(instructionsLabel);
    }

    private void requestUsername() {
        String input = JOptionPane.showInputDialog(null, "Enter your username (3-15 characters):");
        if (input != null) {  // Checks iff user didn't click cancel
            if (isUsernameValid(input)) {
                username = input;
                gameApp.startGame();
            } else {
                showUsernameError();
            }
        }
    }

    private boolean isUsernameValid(String username) {
        return username != null && username.length() >= 3 && username.length() <= 15;
    }

    private void showUsernameError() {
        JOptionPane.showMessageDialog(null, 
            "Username must be between 3 and 15 characters!", 
            "Invalid Username", 
            JOptionPane.ERROR_MESSAGE);
    }

    public String getUsername() {
        return username;
    }
} 
