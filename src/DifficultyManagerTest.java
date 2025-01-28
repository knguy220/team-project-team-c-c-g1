public class DifficultyManagerTest {

    public static void main(String[] args) {
        // Create a DifficultyManager instance
        DifficultyManager dm = new DifficultyManager();

        // Load the difficulty configuration file
        if (dm.loadConfig("difficulty_config.txt")) {
            System.out.println("Difficulty configuration loaded successfully!");

            // Retrieve and print the settings for different difficulties
            DifficultyManager.DifficultySettings easySettings = dm.getSettings("Easy");
            DifficultyManager.DifficultySettings mediumSettings = dm.getSettings("Medium");
            DifficultyManager.DifficultySettings hardSettings = dm.getSettings("Hard");

            // Validate settings
            if (easySettings != null) {
                System.out.println("Easy Settings:");
                System.out.println("Enemy Speed: " + easySettings.getEnemySpeed());
                System.out.println("Spawn Rate: " + easySettings.getSpawnRate());
                System.out.println("Player Health: " + easySettings.getPlayerHealth());
            } else {
                System.out.println("Easy settings not found or invalid.");
            }

            if (mediumSettings != null) {
                System.out.println("Medium Settings:");
                System.out.println("Enemy Speed: " + mediumSettings.getEnemySpeed());
                System.out.println("Spawn Rate: " + mediumSettings.getSpawnRate());
                System.out.println("Player Health: " + mediumSettings.getPlayerHealth());
            } else {
                System.out.println("Medium settings not found or invalid.");
            }

            if (hardSettings != null) {
                System.out.println("Hard Settings:");
                System.out.println("Enemy Speed: " + hardSettings.getEnemySpeed());
                System.out.println("Spawn Rate: " + hardSettings.getSpawnRate());
                System.out.println("Player Health: " + hardSettings.getPlayerHealth());
            } else {
                System.out.println("Hard settings not found or invalid.");
            }

        } else {
            System.out.println("Failed to load difficulty configuration. Check the file path or format.");
        }
    }
}

