package project2.system;

import project2.GameContext;
import project2.entity.EntityVisitor;

public abstract class EntitySystem implements GameSystem {
    protected GameContext ctx;

    @Override
    public void tick(GameContext ctx) {
        this.ctx = ctx;
        this.ctx.visit(getVisitor());
    }

    public abstract EntityVisitor getVisitor();

}
