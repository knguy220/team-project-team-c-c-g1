import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.util.ArrayList;
import java.util.List;

public class Obstacles {
    private List<GObject> obstacles; // List to store all obstacles

    public Obstacles() {
        obstacles = new ArrayList<>();
    }

    // Create a wall (vertical or horizontal)
    public void createWall(double x, double y, double w, double l, boolean isVert) {
        GLine wall;
        if (isVert) {
            wall = new GLine(x, y, x, y + l); // Create a vertical wall
        } else {
            wall = new GLine(x, y, x + l, y); // Create a horizontal wall
        }
        wall.setLineWidth((int)w);
        obstacles.add(wall); // Add the wall to the obstacle list
    }

    // Create a rock (GImage)
    public void createRock(double x, double y, double scale) {
        GImage rock = new GImage("rock.png", x, y); // Replace with the path to your rock image
        rock.scale(scale); // Scale the rock size
        obstacles.add(rock); // Add the rock to the obstacle list
    }

    // Get the list of all obstacles
    public List<GObject> getObstacles() {
        return obstacles;
    }

    // Render obstacles to the screen
    public void renderObstacles(GraphicsProgram program) {
        for (GObject obstacle : obstacles) {
            program.add(obstacle); // Add each obstacle to the screen
        }
    }

    // Check for collisions with player or any other object (returns true if collision detected)
    public boolean checkCollision(double playerX, double playerY, double playerWidth, double playerHeight) {
        for (GObject obstacle : obstacles) {
            if (obstacle instanceof GLine) {
                // Collision check for a wall (GLine)
                GLine line = (GLine) obstacle;
                // Simple collision logic (you can improve it)
                if (line.getStartPoint().getX() < playerX + playerWidth &&
                    line.getEndPoint().getX() > playerX &&
                    line.getStartPoint().getY() < playerY + playerHeight &&
                    line.getEndPoint().getY() > playerY) {
                    return true;
                }
            } else if (obstacle instanceof GImage) {
                // Collision check for a rock (GImage)
                GImage image = (GImage) obstacle;
                if (playerX + playerWidth > image.getX() && playerX < image.getX() + image.getWidth() &&
                    playerY + playerHeight > image.getY() && playerY < image.getY() + image.getHeight()) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision
    }
}
