package project2.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import project2.GameContext;
import project2.system.PhysicsSystem;

public abstract class AbstractEntity implements Entity {

    protected boolean alive = true;
    protected Body body;

    @Override
    public void added(GameContext ctx, Vector2 position) {
        this.body = createBody(ctx.getSystem(PhysicsSystem.class), position);
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    public abstract Body createBody(PhysicsSystem physicsSystem, Vector2 position);

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public boolean pendingRemoval() {
        return !alive;
    }
}
