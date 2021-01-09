package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import project2.entity.*;

public class PlayerSystem extends EntitySystem implements EntityVisitor {

    private final Vector2 playerPos = new Vector2();

    public Vector2 getPlayerPos() {
        return playerPos;
    }

    @Override
    public void visitPlayer(PlayerEntity player) {
        Body body = player.getBody();

        Vector2 speed = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speed.y += 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speed.y -= 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speed.x -= 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speed.x += 1.0f;
        }

        speed = speed.nor();
        speed.scl(5f);

        // f = mdv/t
        Vector2 f = speed.sub(body.getLinearVelocity())
                         .scl(body.getMass());

        body.applyLinearImpulse(f, Vector2.Zero, true);

        playerPos.set(player.getPosition());
    }

    @Override
    public void visitEnemy(EnemyEntity enemy) {

    }

    @Override
    public void visitCherry(CherryEntity cherry) {

    }

    @Override
    public void visitMap(MapEntity map) {

    }

    @Override
    public EntityVisitor getVisitor() {
        return this;
    }

}
