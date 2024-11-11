import acm.graphics.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
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

    public void addActionListener(MouseListener listener) {
        rect.addMouseListener(listener);
        label.addMouseListener(listener);
    }
}
