package project2.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Map {

    private boolean[][] wall;

    private int sizeX;
    private int sizeY;

    private Vector2 playerSpawnPoint = new Vector2(1.0f, 1.0f);

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.wall = new boolean[sizeY][sizeX];
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

    public Vector2 getPlayerSpawnPoint() {
        return playerSpawnPoint;
    }

    public void setPlayerSpawnPoint(int x, int y) {
        this.playerSpawnPoint.set(x, y);
    }

}
