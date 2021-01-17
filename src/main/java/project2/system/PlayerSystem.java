package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import project2.GameContext;
import project2.entity.*;

public class PlayerSystem extends EntitySystem implements EntityVisitor {

    private PlayerEntity player = null;


    @Override
    public void tick(GameContext ctx) {
        this.player = null;
        super.tick(ctx);
    }

    @Override
    public void visitPlayer(PlayerEntity player) {
        Body body = player.getBody();
        this.player = player;

        this.player.timeStep(Gdx.graphics.getDeltaTime());

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
    }

    @Override
    public EntityVisitor getVisitor() {
        return this;
    }

    public boolean isPlayerAlive() {
        return this.player != null;
    }

    public Vector2 getPlayerPos() {
        if (this.player == null) {
            return null;
        }
        return this.player.getPosition();
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }
}
