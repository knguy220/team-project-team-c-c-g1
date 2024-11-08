import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class GameOverScreen extends GraphicsProgram implements ActionListener {
	private GLabel title;
	private GLabel score;
	private GLabel again;
	private GRect restart;
	private GImage icon;
	
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600; 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        addMouseListeners();
        
		title = new GLabel ("Game Over!");
		title.setColor(Color.black);
		title.setFont("Arial-BOLD-36");
		double titleX = (WINDOW_WIDTH - title.getWidth()) / 2;
		double titleY = WINDOW_HEIGHT / 6; 
		title.setLocation(titleX, titleY);
		add(title);
		    
		score = new GLabel (/*userName +*/ "-----'s score: " + /*userScore + */ "---- points", WINDOW_HEIGHT/3, WINDOW_WIDTH/2);
		score.setColor(Color.black);
		score.setFont("Arial-BOLD-24");
		double scoreX = (WINDOW_WIDTH - score.getWidth()) / 2; 
	    double scoreY = WINDOW_HEIGHT / 2; 
	    score.setLocation(scoreX, scoreY);
	    add(score);
	      
	    // Restart Icon
        icon = new GImage("restart.jpg");
        icon.setSize(75, 75);
        double iconX = (WINDOW_WIDTH - icon.getWidth()) / 2;
        double iconY = WINDOW_HEIGHT * 0.75;
        icon.setLocation(iconX, iconY);
        add(icon);

        // "Play Again?" Label
        again = new GLabel("Play Again?");
        again.setColor(Color.black);
        again.setFont("Arial-BOLD-30");
        double againX = (WINDOW_WIDTH - again.getWidth()) / 2; 
        double againY = iconY - 10; 
        again.setLocation(againX, againY);
        add(again);
	    
	    //test 2:15
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void MouseReleased (MouseEvent e) {
		
	}
	public void mouseClicked(MouseEvent e) {
        GObject clickedObject = getElementAt(e.getX(), e.getY());

        if (clickedObject == icon) {
            restartGame();
        }
    }
	
	private void restartGame() {
        // Logic to restart the game, such as resetting variables or going to the start screen.
        System.out.println("Restarting game...");
        // You can replace this print statement with actual restart logic.
    }
	
	public void MouseMoved (MouseEvent e) {
		
	}

	public static void main(String args[]) {
		new GameOverScreen().start();
	}
}
