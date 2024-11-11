import acm.program.GraphicsProgram;
import java.awt.event.MouseEvent;

public class GameApp extends GraphicsProgram {
    // Screens for the game - start screen and settings screen
    private StartScreen2 startScreen;
    private SettingsScreen settingsScreen;

    /**
     * Initializes the game app by setting up the start and settings screens
     * and enabling mouse interactions. Starts by displaying the start screen.
     */
    public void init() {
        startScreen = new StartScreen2(this);
        settingsScreen = new SettingsScreen(this);
        showStartScreen(); // Initial screen displayed

        addMouseListeners(); // Enables interaction with sliders and buttons
    }

    /**
     * Displays the start screen by making it visible.
     */
    public void showStartScreen() {
        startScreen.show();
    }

    /**
     * Transitions from start screen to settings screen.
     */
    public void showSettingsScreen() {
        startScreen.hide(); // Hide the start screen
        settingsScreen.show(); // Show the settings screen
    }

    /**
     * Handles mouse click events to detect button clicks on the settings screen.
     * This checks if the Apply or Back buttons were clicked if the user is not 
     * currently dragging a slider.
     *
     * @param e MouseEvent containing the coordinates of the click
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	
    	
        // Only process button clicks if no slider is being dragged
        if (!settingsScreen.isDraggingSlider()) {
            if (settingsScreen.isApplyButtonClicked(e.getX(), e.getY())) {
                settingsScreen.applySettings(); // Apply changes to settings
            } else if (settingsScreen.isBackButtonClicked(e.getX(), e.getY())) {
                settingsScreen.goBack(); // Return to the start screen
            }
        }
    }

    /**
     * Handles mouse drag events for moving the sliders on the settings screen.
     *
     * @param e MouseEvent containing the coordinates of the drag
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        settingsScreen.handleSliderDrag(e); // Delegate drag handling to the settings screen
    }

    /**
     * Handles mouse release events to reset dragging state after adjusting sliders.
     *
     * @param e MouseEvent from releasing the mouse button
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        settingsScreen.handleMouseReleased(); // Reset dragging flags for sliders
    }

    /**
     * Main entry point for the application, which starts the GraphicsProgram.
     */
    public static void main(String[] args) {
        new GameApp().start();
    }

    /**
     * Placeholder for any main program logic if needed in the future.
     */
    @Override
    public void run() {
    }
}
