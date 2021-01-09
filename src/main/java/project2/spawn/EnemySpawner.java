package project2.spawn;

import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.EnemyEntity;
import project2.entity.PlayerEntity;

public class EnemySpawner extends AbstractSpawner {

    private EnemyEntity enemy;

    public EnemySpawner(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        return enemy == null || !enemy.isAlive();
    }

    @Override
    public void spawn(GameContext ctx) {
        this.enemy = new EnemyEntity();
        ctx.addEntity(enemy, new Vector2(x, y));
    }
}
