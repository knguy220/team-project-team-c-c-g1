// SOURCES TO HELP
// https://www.youtube.com/watch?v=ZM8yIIRGruM&list=PLWms45O3n--5vDnNd6aiu1CSWX3JlCU1n&index=17
//https://youtu.be/FZWX5WoGW00?si=TeGvPnH4x5-LjkaU
//https://youtu.be/_SJU99LU1IQ?si=m5tqsO1litmxD9sN
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StartScreen extends JFrame {
    private JTextField usernameField;
    private BackgroundPanel backgroundPanel;

    public StartScreen() {
        // FRAME
        setTitle("Commander Dez");
        setSize(800, 600);  // Adjust this as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // BACKGROUND PANEL
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // TITLE LABEL
        JLabel titleLabel = new JLabel("Commander Dez", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 56));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        backgroundPanel.add(titleLabel);

        // SETTINGS BUTTON
        JButton settingsButton = new JButton("Settings");
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(settingsButton);

        // INSTRUCTIONS BUTTON
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions();
            }
        });
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(instructionsButton);

        // ENTER USERNAME BUTTON
        JButton enterUsernameButton = new JButton("Enter Username");
        enterUsernameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterUsername();
            }
        });
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        backgroundPanel.add(enterUsernameButton);

        // START BUTTON
        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        backgroundPanel.add(startButton);

        add(backgroundPanel);  // Add the background panel to the frame
        setVisible(true);
    }

    // BACKGROUND IMAGE
    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
            	backgroundImage = ImageIO.read(new File("src/image/istockphoto-1323844652-612x612.jpg"));
                if (backgroundImage == null) {
                    throw new IOException("Image file not found.");
                }
            } catch (IOException e) {
                System.err.println("Error loading background image: " + e.getMessage());
                backgroundImage = null; 
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.BLACK);  // Set background color to black if image fails to load
                g.fillRect(0, 0, getWidth(), getHeight());  // Fill the entire panel with black
            }
        }
    }

    // INSTRUCTIONS
    private void showInstructions() {
        JTextArea instructionsArea = new JTextArea(
            "The Goal is to stay alive as long as possible and protect your ship. "
            + "To move, use the W (Forward), A (Left), S (Backwards), D (Right) keys. "
            + "Use your mouse to fire and aim. Key 1 selects your gun and Key 2 selects your melee. "
            + "Keys 3, 4, and 5 select your power-ups. Use E to pick up health and ammo."
        );
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Instructions",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ENTER USERNAME
    private void enterUsername() {
        usernameField = new JTextField();
        int result = JOptionPane.showConfirmDialog(this, usernameField, "Enter Username", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            JOptionPane.showMessageDialog(this, "Username: " + username);
        }
    }

    // START GAME
    private void startGame() {
        JOptionPane.showMessageDialog(this, "Game Starting!", "Start", JOptionPane.INFORMATION_MESSAGE);
    }

    // SETTINGS
    private void openSettings() {
        dispose();  // CLOSES STARTSCREEN
        SettingsScreen settingsScreen = new SettingsScreen();
        settingsScreen.start();
    }
    // NNED TO ADD FUNCTION TO RETURN TO START SCREEN AFTER CLOSING SETTINGS
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartScreen());
    }
}

