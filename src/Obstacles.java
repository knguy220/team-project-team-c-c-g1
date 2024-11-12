import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;


public class Obstacles extends GraphicsProgram {
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
	public void init() {
		setSize(800,800);
		createWall(200,200,10,100,true);
		add(getWall());
		createWall(400,400,10,100,false);
		add(getWall());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Obstacles().start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
