import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsScreen extends GraphicsProgram {
    private GLabel titleLabel;
    private GLabel musicLabel;
    private GLabel audioLabel;
    private GLabel musicValueLabel;
    private GLabel audioValueLabel;
    private GButton applyButton;
    private GButton backButton;
    private GButton increaseMusicButton;
    private GButton decreaseMusicButton;
    private GButton increaseAudioButton;
    private GButton decreaseAudioButton;
    private int musicVolume = 50;
    private int audioVolume = 50;

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    @Override
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Title Label
        titleLabel = new GLabel("Settings", WINDOW_WIDTH / 2 - 100, 50);
        titleLabel.setFont("Arial-BOLD-32");
        add(titleLabel);

        // Music Volume Label and Value
        musicLabel = new GLabel("Music Volume:", WINDOW_WIDTH / 4, 150);
        musicLabel.setFont("Arial-BOLD-18");
        add(musicLabel);

        musicValueLabel = new GLabel(String.valueOf(musicVolume), WINDOW_WIDTH / 2 + 50, 150);
        musicValueLabel.setFont("Arial-BOLD-18");
        add(musicValueLabel);

        // Music Volume Adjust Buttons
        increaseMusicButton = new GButton("+", "Arial-BOLD-18", WINDOW_WIDTH / 2 + 100, 135, 40, 40, Color.BLACK, Color.WHITE);
        decreaseMusicButton = new GButton("-", "Arial-BOLD-18", WINDOW_WIDTH / 2, 135, 40, 40, Color.BLACK, Color.WHITE);
        add(increaseMusicButton.getRect());
        add(increaseMusicButton.getMessage());
        add(decreaseMusicButton.getRect());
        add(decreaseMusicButton.getMessage());

        // Game Audio Volume Label and Value
        audioLabel = new GLabel("Game Audio Volume:", WINDOW_WIDTH / 4, 250);
        audioLabel.setFont("Arial-BOLD-18");
        add(audioLabel);

        audioValueLabel = new GLabel(String.valueOf(audioVolume), WINDOW_WIDTH / 2 + 50, 250);
        audioValueLabel.setFont("Arial-BOLD-18");
        add(audioValueLabel);

        // Game Audio Volume Adjust Buttons
        increaseAudioButton = new GButton("+", "Arial-BOLD-18", WINDOW_WIDTH / 2 + 100, 235, 40, 40, Color.BLACK, Color.WHITE);
        decreaseAudioButton = new GButton("-", "Arial-BOLD-18", WINDOW_WIDTH / 2, 235, 40, 40, Color.BLACK, Color.WHITE);
        add(increaseAudioButton.getRect());
        add(increaseAudioButton.getMessage());
        add(decreaseAudioButton.getRect());
        add(decreaseAudioButton.getMessage());

        // Apply Button
        applyButton = new GButton("Apply", "Arial-BOLD-18", WINDOW_WIDTH / 3, 400, 120, 40, Color.BLACK, Color.WHITE);
        add(applyButton.getRect());
        add(applyButton.getMessage());

        // Back Button
        backButton = new GButton("Back", "Arial-BOLD-18", WINDOW_WIDTH / 2, 400, 120, 40, Color.BLACK, Color.WHITE);
        add(backButton.getRect());
        add(backButton.getMessage());

        addMouseListeners();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GObject clickedObject = getElementAt(e.getX(), e.getY());

        // Check which button was clicked
        if (clickedObject == increaseMusicButton.getRect() || clickedObject == increaseMusicButton.getMessage()) {
            if (musicVolume < 100) musicVolume += 10;
            musicValueLabel.setLabel(String.valueOf(musicVolume));
        } else if (clickedObject == decreaseMusicButton.getRect() || clickedObject == decreaseMusicButton.getMessage()) {
            if (musicVolume > 0) musicVolume -= 10;
            musicValueLabel.setLabel(String.valueOf(musicVolume));
        } else if (clickedObject == increaseAudioButton.getRect() || clickedObject == increaseAudioButton.getMessage()) {
            if (audioVolume < 100) audioVolume += 10;
            audioValueLabel.setLabel(String.valueOf(audioVolume));
        } else if (clickedObject == decreaseAudioButton.getRect() || clickedObject == decreaseAudioButton.getMessage()) {
            if (audioVolume > 0) audioVolume -= 10;
            audioValueLabel.setLabel(String.valueOf(audioVolume));
        } else if (clickedObject == applyButton.getRect() || clickedObject == applyButton.getMessage()) {
            applySettings();
        } else if (clickedObject == backButton.getRect() || clickedObject == backButton.getMessage()) {
            exitSettings();
        }
    }

    private void applySettings() {
        // Save settings or show confirmation message
        GLabel confirmationLabel = new GLabel("Settings applied!", WINDOW_WIDTH / 2 - 50, 500);
        confirmationLabel.setFont("Arial-BOLD-16");
        confirmationLabel.setColor(Color.BLUE);
        add(confirmationLabel);
        
        // Remove the confirmation message after 2 seconds
        new javax.swing.Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(confirmationLabel);
            }
        }).start();
    }

    private void exitSettings() {
        // Logic to go back to the start screen
        getGCanvas().getGraphics().clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String[] args) {
        new SettingsScreen().start();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


