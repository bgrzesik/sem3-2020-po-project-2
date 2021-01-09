package project2.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import project2.entity.MapEntity;
import project2.map.GameMap;
import project2.map.WallRenderer;

public class MapRenderer extends AbstractRenderer<MapEntity> {
    private FrameBuffer frameBuffer = null;
    private GameMap map;

    @Override
    public void render(SpriteBatch batch) {
        if (this.frameBuffer == null) {
            createFramebuffer();
        }

        Texture mapTexture = this.frameBuffer.getColorBufferTexture();
        batch.draw(mapTexture, -0.5f, -0.5f, this.map.getSizeX(), this.map.getSizeY());
    }


    @Override
    public void setEntity(MapEntity entity) {
        super.setEntity(entity);
        this.map = entity.getMap();
    }

    public void createFramebuffer() {
        this.frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,
                                      map.getSizeX() * 128, map.getSizeY() * 128,
                                      false);

        SpriteBatch batch = new SpriteBatch();


        TextureRegion[][] atlas = renderingSystem.getAtlas();

        this.frameBuffer.bind();

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, map.getSizeX() * 128, map.getSizeY() * 128);

        Matrix4 proj = new Matrix4();
        proj.setToOrtho(0.0f, map.getSizeX(), map.getSizeY(), 0.0f, -0.1f, 1.0f);

        batch.setProjectionMatrix(proj);
        batch.begin();

        for (int x = 0; x < map.getSizeX(); x++) {
            for (int y = 0; y < map.getSizeY(); y++) {
                if (!map.isWall(x, y)) {
                    continue;
                }

                int neighbors = 0b00000000;

                for (int i = 0; i < WallRenderer.BYTE_ORDER.length; i++) {
                    Vector2 pos = new Vector2(x, y).add(WallRenderer.BYTE_ORDER[i]);
                    boolean present;

                    if (pos.x < 0 || pos.x >= map.getSizeX()) {
                        present = false;
                    } else if (pos.y < 0 || pos.y >= map.getSizeY()) {
                        present = false;
                    } else {
                        present = map.isWall((int) pos.x, (int) pos.y);
                    }

                    neighbors |= (present ? 1 : 0) << i;
                }

                new WallRenderer(x, y, neighbors)
                        .render(atlas, batch);
            }
        }

        batch.end();

        FrameBuffer.unbind();
    }

}

