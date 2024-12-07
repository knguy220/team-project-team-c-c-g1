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
            System.out.println("Sound file loaded successfully: " + filename);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound file: " + e.getMessage());
        }
    }

    // Method to set the volume of the sound
    public void setVolume(float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
            System.out.println("Volume set to: " + volume);
        } else {
            System.err.println("Volume control not supported for this clip.");
        }
    }

    // Method to loop the sound
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Sound loop started.");
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to play the sound once
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
            System.out.println("Sound play started.");
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to stop the sound
    public void stop() {
        if (clip != null) {
            clip.stop();
            System.out.println("Sound stopped.");
        } else {
            System.err.println("Clip is not initialized.");
        }
    }

    // Method to close the clip and release resources
    public void close() {
        if (clip != null) {
            clip.close();
            System.out.println("Clip closed.");
        }
    }
}


