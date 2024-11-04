import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;

public class GButton extends GraphicsProgram {
	private GRect rect;
	private GLabel message;
	
	GButton(GRect r, GLabel m) {
		rect = r;
		message = m;
	}
	public void sizeLabel(double w, double h) {
		message.scale(w, h);
	}
	public void setRectColor(Color e) {
		rect.setFillColor(e);
	}
	public void setLabelMessage(String s) {
		message.setLabel(s);
	}
	public void setLabelColor(Color e) {
		message.setColor(e);
	}
	public void setLabelFont(Font f) {
		message.setFont(f);
	}
	
	public static void main(String[] args) {
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
} 
