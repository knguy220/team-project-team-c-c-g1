/*import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;



public class InstructionsPane extends GraphicsProgram {
	private GRect rect;
	private GLabel goal;
	private GLabel moving;
	private GLabel attacking;
	private GLabel selecting;
	private GLabel select;
	private GLabel pickingUp;
	private GButton button;
	public static final int PROGRAM_WIDTH = 600;
	public static final int PROGRAM_HEIGHT = 600;
	
	@Override
	public void init() {
		setSize(PROGRAM_WIDTH,PROGRAM_HEIGHT);
		
		rect = new GRect(PROGRAM_WIDTH/10,PROGRAM_HEIGHT/10,450,200);
		rect.setColor(Color.gray);
		rect.setFilled(true);
		add(rect);
		goal = new GLabel("The GOAL is to stay alive as long as possible and protect your ship.", PROGRAM_WIDTH/9,PROGRAM_HEIGHT/8);
		goal.setFont("SERIF-BOLD-15");
		goal.setColor(Color.blue);
		add(goal);
		moving = new GLabel("To move, use the W, A, S, D keys.", PROGRAM_WIDTH/6,PROGRAM_HEIGHT/6);
		moving.setFont("SERIF-13");
		moving.setColor(Color.blue);
		add(moving);
		attacking = new GLabel("Use your mouse to fire and aim.", PROGRAM_WIDTH/6,moving.getY()+24);
		attacking.setFont("SERIF-13");
		attacking.setColor(Color.blue);
		add(attacking);
		selecting = new GLabel("Key 1 selects your gun and Key 2 selects your melee.", PROGRAM_WIDTH/6,moving.getY()+48);
		selecting.setFont("SERIF-13");
		selecting.setColor(Color.blue);
		add(selecting);
		select = new GLabel("Keys 3, 4, and 5 select your power-ups.",PROGRAM_WIDTH/6,moving.getY()+60);
		select.setFont("SERIF-13");
		select.setColor(Color.blue);
		add(select);
		pickingUp = new GLabel("Use E to pick up health and ammo.", PROGRAM_WIDTH/6,select.getY()+24);
		pickingUp.setFont("SERIF-13");
		pickingUp.setColor(Color.blue);
		add(pickingUp);
		button = new GButton("BACK","ARIAL-18",rect.getWidth()/2,rect.getHeight()*2,100,50,Color.black,Color.blue);
		add(button.getRect());
		add(button.getMessage());
		
		addMouseListeners();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject clickedObject = getElementAt(e.getX(),e.getY());
		if (clickedObject == button.getRect() || clickedObject == button.getMessage()) {
			exitInstructions();
		}
	}
	
	private void exitInstructions() {
		getGCanvas().getGraphics().clearRect(0,0,PROGRAM_WIDTH,PROGRAM_HEIGHT);
	}
	
	public static void main(String[] args) {
		new InstructionsPane().start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}*/
