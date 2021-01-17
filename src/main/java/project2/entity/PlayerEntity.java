package project2.entity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import project2.GameContext;
import project2.system.PhysicsSystem;

public class PlayerEntity extends AbstractEntity {

    public static final float MAX_CHERRY_TIME = 10.0f;
    public static final float CHERRY_TIME = 2.0f;

    private float cherryTime = 0.0f;
    private int lives;

    public PlayerEntity(int lives) {
        this.lives = lives;
    }

    @Override
    public Body createBody(PhysicsSystem physicsSystem, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        this.body = physicsSystem.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setPosition(Vector2.Zero);
        circle.setRadius(0.45f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = circle;
        fixDef.density = 1;
        fixDef.restitution = 0;

        this.body.createFixture(fixDef);
        this.body.setUserData(this);
        return this.body;
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visitPlayer(this);
    }

    public void pickUpCherry() {
        this.cherryTime += CHERRY_TIME;
        this.cherryTime = MathUtils.clamp(this.cherryTime, 0, MAX_CHERRY_TIME);
    }

    public boolean isChasing() {
        return this.cherryTime > 0.0f;
    }

    public void timeStep(float dt) {
        this.cherryTime -= dt;
        this.cherryTime = MathUtils.clamp(this.cherryTime, 0, MAX_CHERRY_TIME);
    }

    public int getLives() {
        return this.lives;
    }

    public void died() {
        this.lives -= 1;
    }
}
