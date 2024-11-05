import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GButton extends GraphicsProgram {
	private static int PROGRAM_WIDTH = 800;
	private static int PROGRAM_HEIGHT = 600;
	private GRect rect;
	private GLabel message;
	
	GButton(double x, double y, double w, double h, String t, String f, Color r, Color l) {
		rect = new GRect(x,y,w,h);
		rect.setColor(r);
		rect.setFilled(true);
		message = new GLabel(t,x+(rect.getWidth()/4),y+(rect.getHeight()/2));
		message.setFont(f);
		message.setColor(l);
	}
	public void setLabelMessage(String s, String f) {
		message.setFont(f);;
		message.setLabel(s);
	}
	public void setUp() {
		add(rect);
		add(message);
	}
	public void setButtonLocation(double x, double y) {
		rect.setLocation(x,y);
		message.setLocation(x+(rect.getWidth()/4), y+(rect.getHeight()/2));
	}
	public void setButtonSize(double w, double h) {
		rect.setSize(w, h);
	}
	public void setButtonColor(Color e, Color t) {
		rect.setColor(e);
		rect.setFilled(true);
		message.setColor(t);
	}
	
	public void init() {
		setSize(PROGRAM_WIDTH,PROGRAM_HEIGHT);
		requestFocus();
	}
	
	public static void main(String[] args) {
		GButton button = new GButton(200,200,100,50,"BUTTON","SANS_SERIF-12",Color.black,Color.green);
		button.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		setUp();
		addMouseListeners();
	}
} 
