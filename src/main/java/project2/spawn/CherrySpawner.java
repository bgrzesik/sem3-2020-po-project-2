package project2.spawn;

import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.CherryEntity;
import project2.entity.EnemyEntity;
import project2.system.PlayerMovementSystem;

public class CherrySpawner extends AbstractSpawner {

    private CherryEntity cherry;

    public CherrySpawner(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        if (cherry == null) {
            return true;
        }
        if (cherry.isAlive()) {
            return false;
        }

        Vector2 playerPos = ctx.getSystem(PlayerMovementSystem.class).getPlayerPos();

        float dist = playerPos.cpy().sub(x, y).len();

        return dist > 10;
    }

    @Override
    public void spawn(GameContext ctx) {
        this.cherry = new CherryEntity();
        ctx.addEntity(cherry, new Vector2(x, y));
    }
}
