Difficulty Levels for the Game

Overview:

This feature introduces multiple difficulty levels (Easy, Medium, Hard) to the game, enhancing its accessibility and replayability. Each difficulty level modifies gameplay parameters such as enemy speed, spawn rates, and player health. To make this system user-friendly, all difficulty settings are defined in a configuration file (difficulty_config.txt) that non-programmers can easily edit. The game dynamically reads these settings and adjusts the gameplay accordingly.

Pseudocode:

Routine for Initializing Game Difficulty

This routine initializes the game difficulty based on the player's selection. It loads settings from a configuration file and applies them to key game components like enemies and the player. It returns a status indicating success or failure.

Set the default status to "failure"
Load the configuration file that contains difficulty settings
If the file loads successfully:
  Retrieve the settings for the selected difficulty level
  If the selected difficulty is valid
    Apply the base spawn rate and enemy speed to the Console class
    Apply the enemy speed to the Enemy class
    Apply the player health to the Player class
    Set the status to "success"
  Otherwise, notify the user of an invalid difficulty selection
If the file fails to load, notify the user of the error
Return the status information

Routine for Spawning Enemies Based on Difficulty
This routine spawns enemies during a wave, adjusting the spawn rate, enemy speed, and enemy count dynamically based on the current difficulty level.

Get the base spawn rate and base enemy speed from the Console class
Calculate the number of enemies for the wave:
  Use the wave number to scale the enemy count
Calculate the spawn delay based on the base spawn rate and the wave number
For each enemy to be spawned:
  Create a timer with the calculated spawn delay
  Spawn an enemy using the scaled speed for the current wave
Add the newly spawned enemies to the game


Difficulty Settings
The game supports three difficulty levels:
- **Easy**: Slower enemies, slower spawn rates, and higher player health.
- **Medium**: Balanced settings for normal gameplay.
- **Hard**: Faster enemies, faster spawn rates, and lower player health.

Configuring Difficulty
The difficulty settings are defined in `difficulty_config.txt`. Place this file in the `src` directory. The format should look like this:

Easy: enemySpeed = 4.0 spawnRate = 800 playerHealth = 150

Medium: enemySpeed = 6.0 spawnRate = 600 playerHealth = 100

Hard: enemySpeed = 8.0 spawnRate = 400 playerHealth = 50

Feature Demonstration:

To confirm the difficulty selection feature is implemented:
Launch the application.
On the main menu, click **Start Game**
Enter a valid username (3-15 characters) in the prompt
The difficulty menu will appear
Select a difficulty (Easy, Medium, Hard)

Observe that:
   - The game starts with the selected difficulty
   - The score and difficulty labels are visible on the game screen
   - Enemies spawn faster, move quicker, or the player's health is adjusted based on the selected difficulty
To confirm:
   - Easy: Enemies move slower, spawn slower, and player health is higher
   - Medium: Balanced settings.
   - Hard: Enemies move faster, spawn faster, and player health is lower

If the non-programming user would like to edit the values:

Open difficulty_config.txt in any text editor (e.g., Notepad).
Locate the desired difficulty section:

Easy:
enemySpeed = 4.0
spawnRate = 800
playerHealth = 150

Medium:
enemySpeed = 6.0
spawnRate = 600
playerHealth = 100

Hard:
enemySpeed = 8.0
spawnRate = 400
playerHealth = 50

Adjust the values:
enemySpeed: Controls how fast enemies move
spawnRate: Controls the delay (in milliseconds) between enemy spawns
playerHealth: Sets the player's starting health

Save the file and restart the game to apply the changes

If difficulty_config.txt is invalid, the program will:
Use default settings for that difficulty level, and log the issue


