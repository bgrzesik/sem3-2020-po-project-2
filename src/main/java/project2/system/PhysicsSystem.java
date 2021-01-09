package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import project2.GameContext;

public class PhysicsSystem implements GameSystem {
    private World world;

    public PhysicsSystem() {
        this.world = new World(new Vector2(0, 0), false);
    }

    @Override
    public void tick(GameContext ctx) {

        float dt = Gdx.graphics.getDeltaTime();
        world.step(dt, 9, 4);

    }

    public World getWorld() {
        return world;
    }
}
