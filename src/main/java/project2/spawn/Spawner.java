package project2.spawn;

import com.badlogic.gdx.math.GridPoint2;
import project2.GameContext;

public interface Spawner {

    GridPoint2 getPosition();

    boolean willSpawn(GameContext ctx);

    void spawn(GameContext ctx);

    void reset();
}
