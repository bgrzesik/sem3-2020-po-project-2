package project2.spawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.EnemyEntity;
import project2.entity.PlayerEntity;

public class EnemySpawner extends AbstractSpawner {

    public static final float RESPAWN_COUNTDOWN = 5f;
    private float countdown = -1f;
    private EnemyEntity enemy;

    public EnemySpawner(GridPoint2 pos) {
        super(pos);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        if (enemy == null) {
            return true;
        }
        if (enemy.isAlive()) {
            return false;
        }

        if (countdown == -1f) {
            countdown = RESPAWN_COUNTDOWN;
        }

        if (countdown <= 0f) {
            countdown = -1f;
            return true;
        }

        countdown -= Gdx.graphics.getDeltaTime();

        return false;
    }

    @Override
    public void spawn(GameContext ctx) {
        this.enemy = new EnemyEntity();
        ctx.addEntity(enemy, new Vector2(this.pos.x, this.pos.y));
    }

    @Override
    public void reset() {
        this.enemy = null;
    }
}
