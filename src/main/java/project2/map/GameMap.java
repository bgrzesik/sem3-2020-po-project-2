package project2.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import project2.spawn.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class GameMap {

    public static final int WALL_COLOR = 0x000000;
    public static final int ENEMY_SPAWNER_COLOR = 0xff0000;
    public static final int CHERRY_SPAWNER_COLOR = 0x00ff00;
    public static final int PLAYER_SPAWNER_COLOR = 0x0000ff;
    public static final int EMPTY_COLOR = 0xffffff;

    private boolean[][] wall;

    private GridPoint2 size;
    private Set<Spawner> spawners = new HashSet<>();

    private int totalPoints = 0;

    public GameMap(AssetManager assetManager, String worldFile) {
        Pixmap pixmap = assetManager.get(worldFile, Pixmap.class);

        this.size = new GridPoint2(pixmap.getWidth(), pixmap.getHeight());
        this.wall = new boolean[this.size.x][this.size.y];

        for (int x = 0; x < this.size.x; x++) {
            for (int y = 0; y < this.size.y; y++) {
                GridPoint2 pos = new GridPoint2(x, y);

                switch ((pixmap.getPixel(x, this.size.y - y - 1) >> 8) & 0xffffff) {
                    case EMPTY_COLOR:
                        this.setWall(pos, false);
                        this.spawners.add(new PointSpawner(pos));
                        this.totalPoints += 1;
                        break;

                    case WALL_COLOR:
                        this.setWall(pos, true);
                        break;

                    case PLAYER_SPAWNER_COLOR:
                        this.setWall(pos, false);
                        this.spawners.add(new PlayerSpawner(pos));
                        break;

                    case CHERRY_SPAWNER_COLOR:
                        this.setWall(pos, false);
                        this.spawners.add(new CherrySpawner(pos));
                        break;

                    case ENEMY_SPAWNER_COLOR:
                        this.setWall(pos, false);
                        this.spawners.add(new EnemySpawner(pos));
                        break;

                    default:
                        throw new RuntimeException("Incorrect pixmap format");
                }

            }
        }
    }

    public boolean isWall(GridPoint2 pos) {
        return isWall(pos.x, pos.y);
    }

    public boolean isWall(int x, int y) {
        return this.wall[x][y];
    }

    public void setWall(GridPoint2 pos, boolean wall) {
        setWall(pos.x, pos.y, wall);
    }

    public void setWall(int x, int y, boolean wall) {
        this.wall[x][y] = wall;
    }

    public GridPoint2 getSize() {
        return size;
    }

    public int getSizeX() {
        return this.size.x;
    }

    public int getSizeY() {
        return this.size.y;
    }

    public Set<Spawner> getSpawners() {
        return Collections.unmodifiableSet(this.spawners);
    }

    public int getTotalPoints() {
        return totalPoints;
    }

}
