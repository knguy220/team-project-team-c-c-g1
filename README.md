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
