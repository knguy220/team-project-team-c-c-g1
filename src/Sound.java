import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    public Sound(String filename) {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename))) {
            // Load the sound file
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound file: " + e.getMessage());
        }
    }

    // Method to set the volume of the sound
    public void setVolume(float volume) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        } else {
            System.err.println("Volume control not supported for this clip.");
        }
    }

    // Method to loop the sound
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to play the sound once
    public void play() {
        if (clip != null) {
            try {
                if (clip.isRunning()) {
                    clip.stop(); // Stop current playback if running
                }
                clip.setFramePosition(0); // Reset to the beginning
                clip.start();
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to stop the sound
    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to close the clip and release resources
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
}



