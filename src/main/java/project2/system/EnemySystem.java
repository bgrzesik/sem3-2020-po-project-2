package project2.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import project2.GameContext;
import project2.ai.Pathfinder;
import project2.entity.*;
import project2.map.GameMap;

import java.util.Collections;
import java.util.List;

public class EnemySystem extends EntitySystem implements EntityVisitor {

    private Pathfinder pathfinder = null;
    private boolean avoidingPlayer = false;


    @Override
    public void tick(GameContext ctx) {
        GameMap map = ctx.getSystem(MapSystem.class).getMap();
        PhysicsSystem physicsSystem = ctx.getSystem(PhysicsSystem.class);
        PlayerSystem playerSystem = ctx.getSystem(PlayerSystem.class);

        if (map == null || !playerSystem.isPlayerAlive()) {
            return;
        }

        this.avoidingPlayer = playerSystem.getPlayer().isChasing();

        pathfinder = new Pathfinder(map, playerSystem.getPlayerPos(), physicsSystem);
        super.tick(ctx);
    }

    @Override
    public void visitEnemy(EnemyEntity enemy) {
        Body body = enemy.getBody();

        Vector2 speed;

        if (avoidingPlayer) {
            speed = getAwayPlayerDirection(enemy);
        } else {
            speed = getToPlayerDirection(enemy);
        }

        if (speed != null) {
            Vector2 f = speed.scl(-2.0f)
                             .sub(body.getLinearVelocity())
                             .scl(body.getMass());
            body.applyLinearImpulse(f, Vector2.Zero, true);
        }
    }

    private Vector2 getAwayPlayerDirection(EnemyEntity enemy) {
        PlayerSystem playerSystem = ctx.getSystem(PlayerSystem.class);
        return enemy.getPosition()
                    .cpy()
                    .sub(playerSystem.getPlayerPos())
                    .scl(-1f)
                    .nor();
    }

    private Vector2 getToPlayerDirection(EnemyEntity enemy) {
        List<Vector2> path = getPath(enemy);

        if (path.size() < 2) {
            return null;
        }

        return path.get(0)
                   .sub(path.get(1))
                   .nor();
    }

    public List<Vector2> getPath(EnemyEntity enemy) {
        if (pathfinder == null) {
            return Collections.emptyList();
        }

        return pathfinder.getPathFrom(enemy.getPosition(), EnemyEntity.RADIUS, entity -> {
            if (enemy == entity) {
                return false;
            }
            return !((entity instanceof CherryEntity) || (entity instanceof PointEntity));
        });
    }

    @Override
    public EntityVisitor getVisitor() {
        return this;
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
