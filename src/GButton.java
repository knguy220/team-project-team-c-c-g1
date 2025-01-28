import acm.graphics.*;
import java.awt.Color;
import java.awt.event.MouseListener;

public class GButton {
    private GRect rect;
    private GLabel label;

    public GButton(String text, int x, int y, int width, int height, Color rectColor, Color labelColor) {
        this.rect = new GRect(x, y, width, height);
        this.rect.setFilled(true);
        this.rect.setFillColor(rectColor);

        this.label = new GLabel(text, x + width / 4, y + height / 1.5);
        this.label.setColor(labelColor);
    }

    public GRect getRect() {
        return rect;
    }

    public GLabel getMessage() {
        return label;
    }

    /**
     * Checks if a point (x, y) is within the boundaries of the button's rectangle.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return True if the point is inside the button; false otherwise.
     */
    public boolean contains(double x, double y) {
        return rect.contains(x, y);
    }

    /**
     * Adds the button components to the canvas.
     */
    public void addToCanvas(GCanvas canvas) {
        canvas.add(rect);
        canvas.add(label);
    }
    
    public void addActionListener(MouseListener listener) {
        rect.addMouseListener(listener);
        label.addMouseListener(listener);
    }
}
