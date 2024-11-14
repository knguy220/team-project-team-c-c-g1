import acm.graphics.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Timer;
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
	private GLabel confirmationLabel;

	public SettingsScreen(GameApp gameApp) {
		this.gameApp = gameApp;
	}

	public void show() {
		gameApp.removeAll();
		int screenWidth = (int) gameApp.getWidth();
		int screenHeight = (int) gameApp.getHeight();

		// Title label
		GLabel titleLabel = new GLabel("Settings", screenWidth / 2 - 50, screenHeight / 6);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
		titleLabel.setColor(Color.BLACK);
		gameApp.add(titleLabel);

		// Music volume label and slider
		GLabel musicLabel = new GLabel("Music Volume", screenWidth / 2 - 100, screenHeight / 3);
		musicLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		gameApp.add(musicLabel);

		GRect musicSliderTrack = new GRect(screenWidth / 2 - 100, screenHeight / 3 + 25, 200, 5);
		musicSliderTrack.setFilled(true);
		musicSliderTrack.setColor(Color.GRAY);
		gameApp.add(musicSliderTrack);

		musicSliderKnob = new GOval(screenWidth / 2 - 100 + musicVolume * 2 - 8, screenHeight / 3 + 20, 16, 16);
		musicSliderKnob.setFilled(true);
		musicSliderKnob.setColor(Color.BLACK);
		gameApp.add(musicSliderKnob);

		musicValueLabel = new GLabel(String.valueOf(musicVolume), screenWidth / 2 + 110, screenHeight / 3 + 30);
		gameApp.add(musicValueLabel);

		// Audio volume label and slider
		GLabel audioLabel = new GLabel("Audio Volume", screenWidth / 2 - 100, screenHeight / 3 + 80);
		audioLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		gameApp.add(audioLabel);

		GRect audioSliderTrack = new GRect(screenWidth / 2 - 100, screenHeight / 3 + 105, 200, 5);
		audioSliderTrack.setFilled(true);
		audioSliderTrack.setColor(Color.GRAY);
		gameApp.add(audioSliderTrack);

		audioSliderKnob = new GOval(screenWidth / 2 - 100 + audioVolume * 2 - 8, screenHeight / 3 + 100, 16, 16);
		audioSliderKnob.setFilled(true);
		audioSliderKnob.setColor(Color.BLACK);
		gameApp.add(audioSliderKnob);

		audioValueLabel = new GLabel(String.valueOf(audioVolume), screenWidth / 2 + 110, screenHeight / 3 + 110);
		gameApp.add(audioValueLabel);

		// Apply button
		applyButton = new GButton("Apply", screenWidth / 2 - 50, screenHeight / 2 + 100, 100, 40, Color.BLACK,
				Color.WHITE);
		gameApp.add(applyButton.getRect());
		gameApp.add(applyButton.getMessage());

		// Back button
		backButton = new GButton("Back", screenWidth / 2 - 50, screenHeight / 2 + 160, 100, 40, Color.BLACK,
				Color.WHITE);
		gameApp.add(backButton.getRect());
		gameApp.add(backButton.getMessage());
	}

	public void hide() {
		gameApp.removeAll();
	}

	public void applySettings() {
		
		confirmationLabel = new GLabel("Settings Applied!", (int) gameApp.getWidth() / 2 - 60,
				(int) gameApp.getHeight() / 2 + 90);
		confirmationLabel.setFont(new Font("Arial", Font.BOLD, 14));
		confirmationLabel.setColor(Color.BLACK);
		gameApp.add(confirmationLabel);

		// Timer to remove the confirmation message after 3 seconds
		Timer timer = new Timer(1000, e -> gameApp.remove(confirmationLabel));
		timer.setRepeats(false);
		timer.start();
	}

	public boolean isApplyButtonClicked(double x, double y) {
		return applyButton != null && applyButton.getRect().contains(x, y);
	}

	public boolean isBackButtonClicked(double x, double y) {
		return backButton != null && backButton.getRect().contains(x, y);
	}

	public void handleSliderDrag(MouseEvent e) {
		int screenWidth = (int) gameApp.getWidth();

		if (musicSliderKnob.contains(e.getX(), e.getY()) || draggingMusicSlider) {
			double newX = Math.min(Math.max(e.getX(), screenWidth / 2 - 100), screenWidth / 2 + 100);
			musicSliderKnob.setLocation(newX - 8, musicSliderKnob.getY());
			musicVolume = (int) ((newX - (screenWidth / 2 - 100)) / 2);
			musicValueLabel.setLabel(String.valueOf(musicVolume));
			draggingMusicSlider = true;
		} else if (audioSliderKnob.contains(e.getX(), e.getY()) || draggingAudioSlider) {
			double newX = Math.min(Math.max(e.getX(), screenWidth / 2 - 100), screenWidth / 2 + 100);
			audioSliderKnob.setLocation(newX - 8, audioSliderKnob.getY());
			audioVolume = (int) ((newX - (screenWidth / 2 - 100)) / 2);
			audioValueLabel.setLabel(String.valueOf(audioVolume));
			draggingAudioSlider = true;
		}
	}

	public void handleMouseReleased() {
		draggingMusicSlider = false;
		draggingAudioSlider = false;
	}

	public boolean isDraggingSlider() {
		return draggingMusicSlider || draggingAudioSlider;
	}
}
