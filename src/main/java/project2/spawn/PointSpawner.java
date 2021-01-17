package project2.spawn;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.CherryEntity;
import project2.entity.PointEntity;
import project2.system.PlayerSystem;

public class PointSpawner extends AbstractSpawner {
    private PointEntity point;

    public PointSpawner(GridPoint2 pos){
        super(pos);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        return point == null;
    }

    @Override
    public void spawn(GameContext ctx) {
        this.point = new PointEntity();
        ctx.addEntity(point, new Vector2(this.pos.x, this.pos.y));
    }

    @Override
    public void reset() {
    }
}
