import acm.graphics.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final int TILE_SIZE = 40;
    private static final int WALL = 1;
    private static final int FLOOR = 0;
    private List<Position> enemySpawnPoints;
    private int[][] map = {};
    private int cols;
    private int rows;

    public Map(int screenWidth, int screenHeight) {
    	
    	cols = (int) (screenWidth/ TILE_SIZE);
    	rows = (int) (screenHeight/ TILE_SIZE);
    	
    	map = new int[rows][cols];
    	
        enemySpawnPoints = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) { // Floor tile
                    enemySpawnPoints.add(new Position(x, y));
                }
            }
        }
        
        generateMap(rows, cols);
    }
    
    private void generateMap (int rows, int cols) {
    	 // Create a simple border map (walls around the edges)
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (y == 0 || x == 0 || y == rows - 1 || x == cols - 1) {
                    map[y][x] = WALL; // Border walls
                } else {
                    map[y][x] = FLOOR; // Open floor
                    enemySpawnPoints.add(new Position(x, y));
                }
            }
        }
    }
    

    public boolean isWall(int x, int y) {
        return map[y][x] == WALL;
    }

    public Position getRandomSpawnPoint() {
        return enemySpawnPoints.get((int) (Math.random() * enemySpawnPoints.size()));
    }

    public void render(GameApp gameApp) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                GRect tile = new GRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                tile.setFilled(true);
                tile.setColor(map[y][x] == WALL ? Color.BLACK : Color.LIGHT_GRAY);
                gameApp.add(tile);
            }
        }
    }

	public static int getTileSize() {
		return TILE_SIZE;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}
    
}