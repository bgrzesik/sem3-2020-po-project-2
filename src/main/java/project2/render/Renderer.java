package project2.render;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import project2.GameContext;
import project2.entity.Entity;
import project2.system.RenderingSystem;

public interface Renderer<E extends Entity> {

    void render(SpriteBatch batch);

    E getEntity();

    void setEntity(E e);

    void setRenderingSystem(RenderingSystem renderingSystem);

}
