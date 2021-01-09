package project2.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import project2.spawn.CherrySpawner;
import project2.spawn.EnemySpawner;
import project2.spawn.PlayerSpawner;
import project2.spawn.Spawner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class GameMap {

    public static final int WALL_COLOR = 0x000000ff;
    public static final int ENEMY_SPAWNER_COLOR = 0xff0000ff;
    public static final int CHERRY_SPAWNER_COLOR = 0x00ff00ff;
    public static final int PLAYER_SPAWNER_COLOR = 0x0000ffff;
    public static final int EMPTY_COLOR = 0xffffffff;

    private boolean[][] wall;

    private int sizeX;
    private int sizeY;

    private Set<Spawner> spawners = new HashSet<>();

    public GameMap(AssetManager assetManager, String worldFile) {
        Pixmap pixmap = assetManager.get(worldFile, Pixmap.class);

        this.sizeX = pixmap.getWidth();
        this.sizeY = pixmap.getHeight();
        this.wall = new boolean[this.sizeY][this.sizeX];

        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getWidth(); y++) {
                int pixel = pixmap.getPixel(x, pixmap.getHeight() - y - 1);

                switch (pixel) {
                    case WALL_COLOR:
                    case EMPTY_COLOR:
                        this.setWall(x, y, pixel == WALL_COLOR);
                        break;

                    case PLAYER_SPAWNER_COLOR:
                        this.spawners.add(new PlayerSpawner(x, y));
                        break;

                    case CHERRY_SPAWNER_COLOR:
                        this.spawners.add(new CherrySpawner(x, y));
                        break;

                    case ENEMY_SPAWNER_COLOR:
                        this.spawners.add(new EnemySpawner(x, y));
                        break;

                    default:
                        throw new RuntimeException("Incorrect pixmap format");
                }

            }
        }
    }

    public boolean isWall(int x, int y) {
        return this.wall[x][y];
    }

    public void setWall(int x, int y, boolean wall) {
        this.wall[x][y] = wall;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Set<Spawner> getSpawners() {
        return Collections.unmodifiableSet(this.spawners);
    }
}
