package project2.spawn;

import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.CherryEntity;
import project2.entity.EnemyEntity;

public class CherrySpawner extends AbstractSpawner {

    private CherryEntity cherry;

    public CherrySpawner(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        return cherry == null || !cherry.isAlive();
    }

    @Override
    public void spawn(GameContext ctx) {
        this.cherry = new CherryEntity();
        ctx.addEntity(cherry, new Vector2(x, y));
    }
}
