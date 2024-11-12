import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;


public class Obstacles {
	private GLine wall;
	private GImage rock;
	
	public void createWall(double x, double y, double w, double l, boolean isVert) {
		if (isVert == true) {
			wall = new GLine(x,y,x,y+l);
		} else {
			wall = new GLine(x,y,x+l,y);
		}
		wall.setLineWidth(w);
		
	}
	public GLine getWall() {
		return wall;
	}
	public void createRock(double x, double y, double s) {
		rock = new GImage("rock.png",x,y);
		rock.scale(s);
	}
	public GImage getRock() {
		return rock;
	}
}
