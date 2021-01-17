package project2.spawn;

import com.badlogic.gdx.math.GridPoint2;
import project2.entity.Entity;

public abstract class AbstractSpawner implements Spawner {

    protected GridPoint2 pos;

    public AbstractSpawner(GridPoint2 pos) {
        this.pos = pos;
    }

    @Override
    public GridPoint2 getPosition() {
        return this.pos;
    }

}
