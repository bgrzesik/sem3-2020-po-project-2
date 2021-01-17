package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import project2.GameContext;
import project2.entity.EnemyEntity;
import project2.entity.Entity;

import java.util.ArrayList;
import java.util.List;

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

    public List<Entity> rayCast(Vector2 a, Vector2 b, float raySize) {
        List<Entity> obstacles = new ArrayList<>();

        Vector2 offset = a.cpy().sub(b).nor().scl(raySize);
        Vector2 offset1 = offset.cpy().rotate90(1);
        Vector2 offset2 = offset.cpy().rotate90(-1);

        RayCastCallback rayCastCallback = (fixture, point1, normal, fraction) -> {
            obstacles.add(((Entity) fixture.getBody().getUserData()));
            return 1.0f;
        };

        world.rayCast(rayCastCallback,
                      a.cpy().add(offset1),
                      b.cpy().add(offset1));

        world.rayCast(rayCastCallback,
                      a.cpy().add(offset2),
                      b.cpy().add(offset2));

        return obstacles;
    }
}
