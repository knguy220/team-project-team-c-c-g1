import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DifficultyManager {

    private final Map<String, DifficultySettings> difficulties = new HashMap<>();

    /**
     * Loads the difficulty configuration file and parses the settings.
     *
     * @param filePath the relative path to the configuration file
     * @return true if the file was loaded and parsed successfully, false otherwise
     */
    public boolean loadConfig(String filePath) {
        try {
            // Use class loader to load the file from the src directory
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                System.err.println("Configuration file not found in classpath: " + filePath);
                return false;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String currentDifficulty = null;
            DifficultySettings settings = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing spaces

                // Check if the line is a difficulty header (e.g., "Easy:")
                if (line.endsWith(":")) {
                    if (currentDifficulty != null && settings != null) {
                        // Save the previous difficulty settings
                        difficulties.put(currentDifficulty, settings);
                    }
                    currentDifficulty = line.substring(0, line.length() - 1).trim();
                    settings = new DifficultySettings();
                } else if (line.contains("=")) {
                    // Parse key-value pairs (e.g., "enemySpeed=4.0")
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        if (settings != null) {
                            switch (key) {
                                case "enemySpeed":
                                    settings.setEnemySpeed(Double.parseDouble(value));
                                    break;
                                case "spawnRate":
                                    settings.setSpawnRate(Integer.parseInt(value));
                                    break;
                                case "playerHealth":
                                    settings.setPlayerHealth(Integer.parseInt(value));
                                    break;
                                default:
                                    System.err.println("Unknown key: " + key);
                            }
                        }
                    } else {
                        System.err.println("Invalid key-value line: " + line);
                    }
                } else if (!line.isEmpty()) {
                    System.err.println("Invalid line: " + line);
                }
            }

            // Save the last difficulty settings
            if (currentDifficulty != null && settings != null) {
                difficulties.put(currentDifficulty, settings);
            }

            reader.close();
            return true;

        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the difficulty settings for the specified difficulty.
     *
     * @param difficulty the name of the difficulty (e.g., "Easy", "Medium", "Hard")
     * @return the DifficultySettings object, or null if not found
     */
    public DifficultySettings getSettings(String difficulty) {
        return difficulties.get(difficulty);
    }

    /**
     * Inner class to hold difficulty settings.
     */
    public static class DifficultySettings {
        private double enemySpeed;
        private int spawnRate;
        private int playerHealth;

        // Default constructor
        public DifficultySettings() {
            this.enemySpeed = 0.0;
            this.spawnRate = 0;
            this.playerHealth = 0;
        }

        // New constructor to initialize all fields
        public DifficultySettings(double enemySpeed, int spawnRate, int playerHealth) {
            this.enemySpeed = enemySpeed;
            this.spawnRate = spawnRate;
            this.playerHealth = playerHealth;
        }

        // Getters and setters
        public double getEnemySpeed() {
            return enemySpeed;
        }

        public void setEnemySpeed(double enemySpeed) {
            this.enemySpeed = enemySpeed;
        }

        public int getSpawnRate() {
            return spawnRate;
        }

        public void setSpawnRate(int spawnRate) {
            this.spawnRate = spawnRate;
        }

        public int getPlayerHealth() {
            return playerHealth;
        }

        public void setPlayerHealth(int playerHealth) {
            this.playerHealth = playerHealth;
        }

        // Validation method to check if the settings are valid
        public boolean isValid() {
            return enemySpeed > 0 && spawnRate > 0 && playerHealth > 0;
        }
    }
}