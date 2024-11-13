import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

public class SettingsScreen {
    private GameApp gameApp;
    private GOval musicSliderKnob;
    private GOval audioSliderKnob;
    private GLabel musicValueLabel;
    private GLabel audioValueLabel;
    private GButton applyButton;
    private GButton backButton;
    private int musicVolume = 50;
    private int audioVolume = 50;
    private boolean draggingMusicSlider = false;
    private boolean draggingAudioSlider = false;

    public SettingsScreen(GameApp gameApp) {
        this.gameApp = gameApp;
    }

    public void show() {
        gameApp.removeAll(); 

        GLabel titleLabel = new GLabel("Settings", 200, 100);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setColor(Color.BLACK);
        gameApp.add(titleLabel);

        GLabel musicLabel = new GLabel("Music Volume", 200, 200);
        musicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(musicLabel);

        GRect musicSliderTrack = new GRect(300, 200, 200, 5);
        musicSliderTrack.setFilled(true);
        musicSliderTrack.setColor(Color.GRAY);
        gameApp.add(musicSliderTrack);

        musicSliderKnob = new GOval(300 + musicVolume * 2 - 8, 195, 16, 16);
        musicSliderKnob.setFilled(true);
        musicSliderKnob.setColor(Color.BLACK);
        gameApp.add(musicSliderKnob);

        musicValueLabel = new GLabel(String.valueOf(musicVolume), 510, 205);
        gameApp.add(musicValueLabel);

        GLabel audioLabel = new GLabel("Audio Volume", 200, 250);
        audioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameApp.add(audioLabel);

        GRect audioSliderTrack = new GRect(300, 250, 200, 5);
        audioSliderTrack.setFilled(true);
        audioSliderTrack.setColor(Color.GRAY);
        gameApp.add(audioSliderTrack);

        audioSliderKnob = new GOval(300 + audioVolume * 2 - 8, 245, 16, 16);
        audioSliderKnob.setFilled(true);
        audioSliderKnob.setColor(Color.BLACK);
        gameApp.add(audioSliderKnob);

        audioValueLabel = new GLabel(String.valueOf(audioVolume), 510, 255);
        gameApp.add(audioValueLabel);

        applyButton = new GButton("Apply", 200, 350, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(applyButton.getRect());
        gameApp.add(applyButton.getMessage());

        backButton = new GButton("Back", 350, 350, 100, 40, Color.BLACK, Color.WHITE);
        gameApp.add(backButton.getRect());
        gameApp.add(backButton.getMessage());
    }

    public void hide() {
        gameApp.removeAll();
    }

    public void handleSliderDrag(MouseEvent e) {
        if (musicSliderKnob.contains(e.getX(), e.getY()) || draggingMusicSlider) {
            double newX = Math.min(Math.max(e.getX(), 300), 500);
            musicSliderKnob.setLocation(newX - 8, musicSliderKnob.getY());
            musicVolume = (int) ((newX - 300) / 2);
            musicValueLabel.setLabel(String.valueOf(musicVolume));
            draggingMusicSlider = true;
        } else if (audioSliderKnob.contains(e.getX(), e.getY()) || draggingAudioSlider) {
            double newX = Math.min(Math.max(e.getX(), 300), 500);
            audioSliderKnob.setLocation(newX - 8, audioSliderKnob.getY());
            audioVolume = (int) ((newX - 300) / 2);
            audioValueLabel.setLabel(String.valueOf(audioVolume));
            draggingAudioSlider = true;
        }
    }

    public void handleMouseReleased() {
        draggingMusicSlider = false;
        draggingAudioSlider = false;
    }

    public boolean isApplyButtonClicked(double x, double y) {
        return applyButton != null && applyButton.getRect().contains(x, y);
    }

    public boolean isBackButtonClicked(double x, double y) {
        return backButton != null && backButton.getRect().contains(x, y);
    }

    public boolean isDraggingSlider() {
        return draggingMusicSlider || draggingAudioSlider;
    }

    public void applySettings() {
        System.out.println("Settings applied: Music Volume = " + musicVolume + ", Audio Volume = " + audioVolume);
        GLabel confirmationLabel = new GLabel("Settings Applied!", 200, 400);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmationLabel.setColor(Color.GREEN);
        gameApp.add(confirmationLabel);
    }

    public void goBack() {
        hide();
        gameApp.showStartScreen();
    }
}

