import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

public class SettingsScreen{
    private GameApp gameApp; 
    private GOval musicSliderKnob; 
    private GOval audioSliderKnob; 
    private GLabel musicValueLabel; 
    private GLabel audioValueLabel; 
    private GButton applyButton; 
    private GButton backButton; 
    private int musicVolume = 50; // Initial music volume setting
    private int audioVolume = 50; // Initial audio volume setting
    private boolean draggingMusicSlider = false; 
    private boolean draggingAudioSlider = false; 

    /**
     * Constructor that initializes SettingsScreen with a reference to the main GameApp.
     *
     * @param gameApp The main GameApp instance to enable screen transitions
     */
    public SettingsScreen(GameApp gameApp) {
        this.gameApp = gameApp;
        
    }

    /**
     * Displays the settings screen, adding UI elements like title, sliders, and buttons.
     * Clears any existing UI elements before adding new ones.
     */
    public void show() {
        gameApp.removeAll(); // Clear any existing UI elements

        // Title label for the settings screen
        GLabel titleLabel = new GLabel("Settings", 200, 100);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setColor(Color.BLACK);
        gameApp.add(titleLabel);

        // Music volume label and slider track
        GLabel musicLabel = new GLabel("Music Volume", 200, 200);
        musicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(musicLabel);

        GRect musicSliderTrack = new GRect(300, 200, 200, 5);
        musicSliderTrack.setFilled(true);
        musicSliderTrack.setColor(Color.GRAY);
        gameApp.add(musicSliderTrack);

        // Music slider knob
        musicSliderKnob = new GOval(300 + musicVolume * 2 - 8, 195, 16, 16);
        musicSliderKnob.setFilled(true);
        musicSliderKnob.setColor(Color.BLACK);
        gameApp.add(musicSliderKnob);

        // Display current music volume
        musicValueLabel = new GLabel(String.valueOf(musicVolume), 510, 205);
        gameApp.add(musicValueLabel);

        // Audio volume label and slider track
        GLabel audioLabel = new GLabel("Audio Volume", 200, 250);
        audioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(audioLabel);

        GRect audioSliderTrack = new GRect(300, 250, 200, 5);
        audioSliderTrack.setFilled(true);
        audioSliderTrack.setColor(Color.GRAY);
        gameApp.add(audioSliderTrack);

        // Audio slider knob
        audioSliderKnob = new GOval(300 + audioVolume * 2 - 8, 245, 16, 16);
        audioSliderKnob.setFilled(true);
        audioSliderKnob.setColor(Color.BLACK);
        gameApp.add(audioSliderKnob);

        // Display current audio volume
        audioValueLabel = new GLabel(String.valueOf(audioVolume), 510, 255);
        gameApp.add(audioValueLabel);

        // Apply button to save settings
        applyButton = new GButton("Apply", 200, 350, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(applyButton.getRect());
        gameApp.add(applyButton.getMessage());

        // Back button to return to the start screen
        backButton = new GButton("Back", 350, 350, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(backButton.getRect());
        gameApp.add(backButton.getMessage());
    }

    /**
     * Hides the settings screen by removing all elements.
     */
    public void hide() {
        gameApp.removeAll();
    }

    /**
     * Handles slider dragging to adjust music or audio volume based on the mouse position.
     *
     * @param e MouseEvent containing the coordinates of the drag
     */
    public void handleSliderDrag(MouseEvent e) {
        if (musicSliderKnob.contains(e.getX(), e.getY()) || draggingMusicSlider) {
            double newX = Math.min(Math.max(e.getX(), 300), 500);
            musicSliderKnob.setLocation(newX - 8, musicSliderKnob.getY());
            musicVolume = (int) ((newX - 300) / 2);
            musicValueLabel.setLabel(String.valueOf(musicVolume));
            draggingMusicSlider = true; // Set dragging flag for music slider
        } else if (audioSliderKnob.contains(e.getX(), e.getY()) || draggingAudioSlider) {
            double newX = Math.min(Math.max(e.getX(), 300), 500);
            audioSliderKnob.setLocation(newX - 8, audioSliderKnob.getY());
            audioVolume = (int) ((newX - 300) / 2);
            audioValueLabel.setLabel(String.valueOf(audioVolume));
            draggingAudioSlider = true; // Set dragging flag for audio slider
        }
    }

    /**
     * Resets the dragging flags for the sliders after the mouse is released.
     */
    public void handleMouseReleased() {
        draggingMusicSlider = false;
        draggingAudioSlider = false;
    }

    /**
     * Checks if the Apply button was clicked.
     *
     * @param x X-coordinate of the click
     * @param y Y-coordinate of the click
     * @return true if the Apply button was clicked, false otherwise
     */
    public boolean isApplyButtonClicked(double x, double y) {
        return applyButton.getRect().contains(x, y);
    }

    /**
     * Checks if the Back button was clicked.
     *
     * @param x X-coordinate of the click
     * @param y Y-coordinate of the click
     * @return true if the Back button was clicked, false otherwise
     */
    public boolean isBackButtonClicked(double x, double y) {
        return backButton.getRect().contains(x, y);
    }

    /**
     * Returns true if either slider is being dragged.
     *
     * @return true if a slider is being dragged, false otherwise
     */
    public boolean isDraggingSlider() {
        return draggingMusicSlider || draggingAudioSlider;
    }

    /**
     * Applies the settings by saving the current volume levels and displays a confirmation message.
     */
    public void applySettings() {
        System.out.println("Settings applied: Music Volume = " + musicVolume + ", Audio Volume = " + audioVolume);
        GLabel confirmationLabel = new GLabel("Settings Applied!", 200, 400);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmationLabel.setColor(Color.GREEN);
        gameApp.add(confirmationLabel);
    }

    /**
     * Returns to the start screen by hiding the settings screen.
     */
    public void goBack() {
        hide();
        gameApp.showStartScreen(); // Transition back to the start screen
    }
}

