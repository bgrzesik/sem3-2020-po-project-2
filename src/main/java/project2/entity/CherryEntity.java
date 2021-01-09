package project2.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import project2.GameContext;
import project2.system.PhysicsSystem;

public class CherryEntity extends AbstractEntity {

    @Override
    public Body createBody(PhysicsSystem physicsSystem, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        this.body = physicsSystem.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setPosition(Vector2.Zero);
        circle.setRadius(0.3f);

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
        visitor.visitCherry(this);
    }

}
