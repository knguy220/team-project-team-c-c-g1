import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DifficultyManager {

    // Map to store difficulty settings
    private Map<String, DifficultySettings> difficulties;

    public DifficultyManager() {
        difficulties = new HashMap<>();
    }

    /**
     * Loads difficulty settings from a configuration file.
     * @param filePath Path to the difficulty configuration file.
     * @return true if the file was loaded successfully, false otherwise.
     */
    public boolean loadConfig(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // Skip empty lines or comments
                if (line.isEmpty() || line.startsWith("#")) continue;

                // Parse difficulty settings
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String difficultyName = parts[0].trim();
                    String[] settings = parts[1].split(",");

                    if (settings.length == 3) {
                        try {
                            double enemySpeed = Double.parseDouble(settings[0].trim());
                            int spawnRate = Integer.parseInt(settings[1].trim());
                            int playerHealth = Integer.parseInt(settings[2].trim());

                            difficulties.put(difficultyName, new DifficultySettings(enemySpeed, spawnRate, playerHealth));
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid format for difficulty settings: " + line);
                        }
                    } else {
                        System.err.println("Invalid number of settings for difficulty: " + line);
                    }
                } else {
                    System.err.println("Invalid difficulty line: " + line);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found: " + filePath);
            return false;
        }
    }

    /**
     * Retrieves settings for a specific difficulty level.
     * @param difficultyName Name of the difficulty (e.g., Easy, Medium, Hard).
     * @return The DifficultySettings object, or null if the difficulty is not found.
     */
    public DifficultySettings getSettings(String difficultyName) {
        return difficulties.getOrDefault(difficultyName, null);
    }

    /**
     * Class to store individual difficulty settings.
     */
    public static class DifficultySettings {
        private double enemySpeed;
        private int spawnRate;
        private int playerHealth;

        public DifficultySettings(double enemySpeed, int spawnRate, int playerHealth) {
            this.enemySpeed = enemySpeed;
            this.spawnRate = spawnRate;
            this.playerHealth = playerHealth;
        }

        public double getEnemySpeed() {
            return enemySpeed;
        }

        public int getSpawnRate() {
            return spawnRate;
        }

        public int getPlayerHealth() {
            return playerHealth;
        }

        public boolean isValid() {
            return enemySpeed > 0 && spawnRate > 0 && playerHealth > 0;
        }
    }
}

