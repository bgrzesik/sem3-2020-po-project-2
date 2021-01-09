package project2.system;

import project2.GameContext;
import project2.entity.Entity;
import project2.entity.EntityVisitor;

import java.util.Set;

public abstract class EntitySystem implements GameSystem {
    protected GameContext ctx;

    @Override
    public void tick(GameContext ctx) {
        this.ctx = ctx;

        EntityVisitor visitor = getVisitor();
        Set<Entity> entities = ctx.getEntities();

        for (Entity entity : entities) {
            entity.accept(visitor);
        }
    }

    public abstract EntityVisitor getVisitor();

}
