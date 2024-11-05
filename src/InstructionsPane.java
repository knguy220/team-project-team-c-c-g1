import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InstructionsPane extends GraphicsProgram implements ActionListener {
	private static final int PROGRAM_WIDTH = 600;
	private static final int PROGRAM_HEIGHT = 600;
	private GRect rect;
	private GLabel goal;
	private GLabel moving;
	private GLabel attacking;
	private GLabel selecting;
	private GLabel pickingUp;
	private GButton button;
	
	public void init() {
		setSize(PROGRAM_WIDTH,PROGRAM_HEIGHT);
	}
	public void setUp() {
		
	}
	
	public void MouseReleased(MouseEvent e) {
		
	}
	public void MouseClicked(MouseEvent e) {
		
	}
	public void MouseMoved(MouseEvent e) {
		
	}
	
	
	public static void main(String[] args) {
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		setUp();
	}
}
