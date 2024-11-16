//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Map {
//    private static final int TILE_SIZE = 40;
//    private static final int WALL = 1;
//    private List<Position> enemySpawnPoints;
//    private int[][] map = {
//        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//        {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
//        {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
//        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
//    };
//
//    public Map() {
//        enemySpawnPoints = new ArrayList<>();
//        for (int y = 0; y < map.length; y++) {
//            for (int x = 0; x < map[y].length; x++) {
//                if (map[y][x] == 0) { // Floor tile
//                    enemySpawnPoints.add(new Position(x, y));
//                }
//            }
//        }
//    }
//
//    public boolean isWall(int x, int y) {
//        return map[y][x] == WALL;
//    }
//
//    public Position getRandomSpawnPoint() {
//        return enemySpawnPoints.get((int) (Math.random() * enemySpawnPoints.size()));
//    }
//
//    public void render(Graphics g) {
//        for (int y = 0; y < map.length; y++) {
//            for (int x = 0; x < map[y].length; x++) {
//                g.setColor(map[y][x] == WALL ? Color.BLACK : Color.LIGHT_GRAY);
//                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
//            }
//        }
//    }
//}
import acm.graphics.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final int TILE_SIZE = 40;
    private static final int WALL = 1;
    private List<Position> enemySpawnPoints;
    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public Map() {
        enemySpawnPoints = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) { // Floor tile
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
}



