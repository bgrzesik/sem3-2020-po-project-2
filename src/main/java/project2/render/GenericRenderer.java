package project2.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import project2.entity.Entity;

public class GenericRenderer extends AbstractRenderer<Entity> {

    private final int atlasCol;
    private final int atlasRow;
    private final Vector2 size;

    public GenericRenderer(int atlasCol, int atlasRow, Vector2 size) {
        this.atlasCol = atlasCol;
        this.atlasRow = atlasRow;
        this.size = size;
    }


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region = renderingSystem.getAtlas()[atlasRow][atlasCol];
        Vector2 position = getEntity()
                .getPosition()
                .cpy()
                .sub(size.cpy().scl(0.5f));

        batch.draw(region,
                   position.x, position.y,
                   size.x / 2.0f, size.y / 2.0f,
                   size.x, size.y,
                   1.0f, 1.0f,
                   getEntity().getBody().getAngle());
    }

}
