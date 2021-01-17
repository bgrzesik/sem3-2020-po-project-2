package project2.spawn;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.CherryEntity;
import project2.system.PlayerSystem;

public class CherrySpawner extends AbstractSpawner {

    private CherryEntity cherry;

    public CherrySpawner(GridPoint2 pos){
        super(pos);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        if (cherry == null) {
            return true;
        }
        if (cherry.isAlive()) {
            return false;
        }

        Vector2 playerPos = ctx.getSystem(PlayerSystem.class).getPlayerPos();

        float dist = playerPos
                .cpy()
                .sub(this.pos.x, this.pos.y)
                .len();

        return dist > 10;
    }

    @Override
    public void spawn(GameContext ctx) {
        this.cherry = new CherryEntity();
        ctx.addEntity(cherry, new Vector2(this.pos.x, this.pos.y));
    }

    @Override
    public void reset() {
        cherry = null;
    }
}
