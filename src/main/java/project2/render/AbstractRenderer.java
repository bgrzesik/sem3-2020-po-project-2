package project2.render;

import project2.GameContext;
import project2.entity.EnemyEntity;
import project2.entity.Entity;
import project2.system.RenderingSystem;

public abstract class AbstractRenderer<E extends Entity> implements Renderer<E> {
    protected E entity;
    protected RenderingSystem renderingSystem;

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public void setEntity(E entity) {
        this.entity = entity;
    }

    @Override
    public void setRenderingSystem(RenderingSystem renderingSystem) {
        this.renderingSystem = renderingSystem;
    }
}
