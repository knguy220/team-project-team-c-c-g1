import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

public class SettingsScreen {
    private GameApp gameApp;
    private GButton applyButton;
    private GButton backButton;
    private boolean draggingMusic = false;
    private boolean draggingAudio = false;
    private boolean openedFromPause;

    private int musicVolume = 50; // Initial values
    private int audioVolume = 50;

    private GRect musicSliderTrack;
    private GOval musicSliderKnob;
    private GRect audioSliderTrack;
    private GOval audioSliderKnob;

    public SettingsScreen(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public void show(boolean openedFromPause) {
        this.openedFromPause = openedFromPause;
        gameApp.removeAll();

        int screenWidth = (int) gameApp.getWidth();
        int screenHeight = (int) gameApp.getHeight();

        // Title
        GLabel titleLabel = new GLabel("Settings", screenWidth / 2 - 60, screenHeight / 6);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setColor(Color.BLACK);
        gameApp.add(titleLabel);

        // Music slider
        GLabel musicLabel = new GLabel("Music Volume", screenWidth / 2 - 100, screenHeight / 3);
        musicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(musicLabel);

        musicSliderTrack = new GRect(screenWidth / 2 - 100, screenHeight / 3 + 20, 200, 10);
        musicSliderTrack.setFilled(true);
        musicSliderTrack.setColor(Color.GRAY);
        gameApp.add(musicSliderTrack);

        musicSliderKnob = new GOval(screenWidth / 2 - 100 + musicVolume * 2 - 8, screenHeight / 3 + 15, 16, 16);
        musicSliderKnob.setFilled(true);
        musicSliderKnob.setColor(Color.BLACK);
        gameApp.add(musicSliderKnob);

        // Audio slider
        GLabel audioLabel = new GLabel("Audio Volume", screenWidth / 2 - 100, screenHeight / 3 + 80);
        audioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(audioLabel);

        audioSliderTrack = new GRect(screenWidth / 2 - 100, screenHeight / 3 + 100, 200, 10);
        audioSliderTrack.setFilled(true);
        audioSliderTrack.setColor(Color.GRAY);
        gameApp.add(audioSliderTrack);

        audioSliderKnob = new GOval(screenWidth / 2 - 100 + audioVolume * 2 - 8, screenHeight / 3 + 95, 16, 16);
        audioSliderKnob.setFilled(true);
        audioSliderKnob.setColor(Color.BLACK);
        gameApp.add(audioSliderKnob);

        // Apply button
        applyButton = new GButton("Apply", screenWidth / 2 - 50, screenHeight / 2 + 100, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(applyButton.getRect());
        gameApp.add(applyButton.getMessage());

        // Back button
        backButton = new GButton("Back", screenWidth / 2 - 50, screenHeight / 2 + 160, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(backButton.getRect());
        gameApp.add(backButton.getMessage());
    }

    public void hide() {
        gameApp.removeAll();
    }

    public boolean isApplyButtonClicked(double x, double y) {
        return applyButton != null && applyButton.getRect().contains(x, y);
    }

    public boolean isBackButtonClicked(double x, double y) {
        return backButton != null && backButton.getRect().contains(x, y);
    }

    // Apply settings when the Apply button is clicked
    public void applySettings() {
        // Update the global settings with the new values
        Settings.setMusicVolume(musicVolume);
        Settings.setAudioVolume(audioVolume);

        // Show "Settings Applied!" for 2 seconds
        GLabel confirmationLabel = new GLabel("Settings Applied!", gameApp.getWidth() / 2 - 80, gameApp.getHeight() / 2 + 50);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 18));
        confirmationLabel.setColor(Color.BLACK);
        gameApp.add(confirmationLabel);

        // Create a timer to remove the confirmation label after 2 seconds
        Timer timer = new Timer(2000, e -> gameApp.remove(confirmationLabel));
        timer.setRepeats(false);
        timer.start();

        // Apply the volume settings
        gameApp.updateVolume();  // Apply the volume updates to the game

        // Debugging statement
        System.out.println("Settings applied: Music Volume = " + musicVolume + ", Audio Volume = " + audioVolume);
    }

    public void handleSliderDrag(MouseEvent e) {
        if (musicSliderKnob.contains(e.getX(), e.getY()) || draggingMusic) {
            double newKnobX = Math.min(Math.max(e.getX(), musicSliderTrack.getX()), musicSliderTrack.getX() + musicSliderTrack.getWidth() - 16);
            musicSliderKnob.setLocation(newKnobX, musicSliderKnob.getY());
            musicVolume = (int) ((newKnobX - musicSliderTrack.getX()) / 2);
            draggingMusic = true;
        } else if (audioSliderKnob.contains(e.getX(), e.getY()) || draggingAudio) {
            double newKnobX = Math.min(Math.max(e.getX(), audioSliderTrack.getX()), audioSliderTrack.getX() + audioSliderTrack.getWidth() - 16);
            audioSliderKnob.setLocation(newKnobX, audioSliderKnob.getY());
            audioVolume = (int) ((newKnobX - audioSliderTrack.getX()) / 2);
            draggingAudio = true;
        }
    }

    public void handleMouseReleased() {
        draggingMusic = false;
        draggingAudio = false;
    }

    public void backToPrevious() {
        hide();
        if (openedFromPause) {
            gameApp.resumeGame(); // Return to the paused game
        } else {
            gameApp.showStartScreen(); // Return to the main menu
        }
    }
}

