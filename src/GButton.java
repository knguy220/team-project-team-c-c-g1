import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;


public class GButton {
	private GRect rect;
	private GLabel message;
	public GButton(String s, String f, double x, double y, double w, double h, Color r, Color l) {
		rect = new GRect(x,y,w,h);
		rect.setColor(r);
		rect.setFilled(true);
		message = new GLabel(s,x+rect.getWidth()/4,y+rect.getHeight()/2);
		message.setFont(f);
		message.setColor(l);
	}
	public GLabel getMessage() {
		return message;
	}
	public GRect getRect() {
		return rect;
	}
}