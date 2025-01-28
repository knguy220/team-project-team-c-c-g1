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
    private GRect inventoryBox;
    private List<GLabel> weaponLabels;

    public Map(GameApp gameApp) {
        this((int) gameApp.getWidth(), (int) gameApp.getHeight()); // Cast dimensions to int
    }


    public Map(int screenWidth, int screenHeight) {
    	this.screenWidth = screenWidth;

    	
    	cols = (int) (screenWidth/ TILE_SIZE);
    	rows = (int) (screenHeight/ TILE_SIZE);
    	
    	map = new int[rows][cols];
    	
    	inventory = new ArrayList<>(); //new
        enemySpawnPoints = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) { // Floor tile
                    enemySpawnPoints.add(new Position(x, y));
                }
            }
        }
        
        generateMap(rows, cols);
        
        fillInventory(screenHeight);
        
    }
    public void fillInventory(int screenHeight){
    	Weapon Gun = new Weapon(WeaponType.GUN, 15);
        inventory.add(Gun);
        
    	Weapon HazmatSuit = new Weapon(WeaponType.HAZMATSUIT, 20);
        inventory.add(HazmatSuit);
        
        Weapon flySwat = new Weapon(WeaponType.FLYSWAT, 45);
        inventory.add(flySwat);
        
        Weapon BugRepellent = new Weapon(WeaponType.BUGREPELLENT, 100);
        inventory.add(BugRepellent);
        
        weaponLabels = new ArrayList<>();
//        System.out.println("Initializing Inventory:");
        for (Weapon weapon : inventory) {
  //          System.out.println("Added to inventory: " + weapon.getWeapon());
        }

        initializeInventoryDisplay(screenHeight);

    }
    private void initializeInventoryDisplay(int screenHeight) {
        int inventoryBoxWidth = screenWidth / 4; // Adjust the width based on how many items to show
        System.out.println(screenHeight);
        int inventoryBoxHeight = 50;
        inventoryBox = new GRect(0, 790, inventoryBoxWidth, 790 + inventoryBoxHeight);
        //inventoryBox = new GRect(0, 800, 360, 50);
        // Change this
        
        
        inventoryBox.setFilled(true);
        inventoryBox.setColor(Color.GRAY);

        weaponLabels = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            Weapon weapon = inventory.get(i);
            //GLabel weaponLabel = new GLabel(weapon.getWeapon().toString(), 10 + i * 60, screenHeight - 30);
            GLabel weaponLabel = new GLabel(weapon.getWeapon().toString(),  i * 90, 820);
            
            
            //weaponLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            weaponLabel.setColor(Color.WHITE);
            weaponLabels.add(weaponLabel);
        }

//        System.out.println("Inventory box and labels initialized.");
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
        renderInventory(gameApp);
    }

    private void renderInventory(GameApp gameApp) {
        // Add inventory box
//    	System.out.println("------------------------------------------------------");
    	System.out.println(inventoryBox == null);
        gameApp.add(inventoryBox);
        System.out.println(inventoryBox);
        inventoryBox.sendToFront();
        //System.out.println("Inventory Box: " + inventoryBox.getBounds());
        for (GLabel label : weaponLabels) {
            System.out.println("Label: " + label.getLabel() + " at " + label.getLocation());
        }


        // Add weapon labels
        for (GLabel weaponLabel : weaponLabels) {
            gameApp.add(weaponLabel);
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