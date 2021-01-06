package project2.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.time.temporal.Temporal;

public class MapLoader {

    public static final int WALL_COLOR = 0x000000ff;
    public static final int ENEMY_SPAWNER_COLOR = 0xff0000ff;
    public static final int CHERRY_SPAWNER_COLOR = 0x00ff00ff;
    public static final int PLAYER_SPAWNER_COLOR = 0x0000ffff;
    public static final int EMPTY_COLOR = 0xffffffff;

    public final AssetManager assetManager;


    public MapLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Map loadMap(String worldFile) {
        Pixmap pixmap = this.assetManager.get(worldFile, Pixmap.class);
        Map map = new Map(pixmap.getWidth(), pixmap.getHeight());

        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getWidth(); y++) {
                int pixel = pixmap.getPixel(x, pixmap.getHeight() - y - 1);

                switch (pixel) {
                    case WALL_COLOR:
                    case EMPTY_COLOR:
                        map.setWall(x, y, pixel == WALL_COLOR);
                        break;

                    case PLAYER_SPAWNER_COLOR:
                        map.setPlayerSpawnPoint(x, y);
                        break;

                    case CHERRY_SPAWNER_COLOR:
                    case ENEMY_SPAWNER_COLOR:
                        break;
                    default:
                        throw new RuntimeException("Incorrect pixmap format");
                }

            }
        }


        return map;
    }
}
