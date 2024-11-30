import acm.graphics.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final int TILE_SIZE = 40;
    private static final int WALL = 1;
    private static final int FLOOR = 0;
    private List<Position> enemySpawnPoints;
    private List<Weapon> inventory;
    private int[][] map = {};
    private int cols;
    private int rows;
    
    int screenWidth;

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
        
        Weapon HazmatSuit = new Weapon(WeaponType.HAZMATSUIT, 20);
        inventory.add(HazmatSuit);
        
        Weapon flySwat = new Weapon(WeaponType.FLYSWAT, 45);
        inventory.add(flySwat);
        
        Weapon BugRepellent = new Weapon(WeaponType.BUGREPELLENT, 100);
        inventory.add(BugRepellent);


    }
    
    private void generateMap (int rows, int cols) {
    	 
        
        addRandomWalls(rows, cols, 20, 4);
    }
    
    private void addRandomWalls(int rows, int cols, int numWalls, int maxWallSize) {
        for (int i = 0; i < numWalls; i++) {
        	 int wallWidth = (int) (Math.random() * maxWallSize) + 1; 
             int wallHeight = (int) (Math.random() * maxWallSize) + 1;
             
             int startX, startY;
            
            do {
                startX = (int) (Math.random() * (cols - wallWidth - 1)) + 1; 
                startY = (int) (Math.random() * (rows - wallHeight - 1)) + 1; 
            } while  (!canPlaceWallCluster(startX, startY, wallWidth, wallHeight));
            
            for (int y = startY; y < startY + wallHeight; y++) {
                for (int x = startX; x < startX + wallWidth; x++) {
                    map[y][x] = WALL;
                }
            }
        }
        
     
    }
    private boolean canPlaceWallCluster(int startX, int startY, int width, int height) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                if (map[y][x] == WALL) { 
                    return false; 
                }
            }
        }
        return true;
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