import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;


public class GameOverScreen extends GraphicsProgram implements ActionListener {
	private GLabel title;
	private GLabel score;
	
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600; 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		title = new GLabel ("Game Over!", WINDOW_WIDTH/3, WINDOW_HEIGHT/6);
		title.setColor(Color.black);
		title.setFont("Arial-BOLD-36");
		add(title);
		score = new GLabel (/*userName +*/ "-----'s score: " + /*userScore + */ "---- points", WINDOW_HEIGHT/3, WINDOW_WIDTH/2);
		score.setColor(Color.black);
		score.setFont("Arial-BOLD-24");
		add(score);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void MouseReleased (MouseEvent e) {
		
	}
	public void MouseClicked (MouseEvent e) {
		
	}
	public void MouseMoved (MouseEvent e) {
		
	}
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	public static void main(String args[]) {
		new GameOverScreen().start();
	}
}
