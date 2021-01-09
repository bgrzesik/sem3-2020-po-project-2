package project2.spawn;

import project2.GameContext;

public interface Spawner {

    int getCoordX();

    int getCoordY();

    boolean willSpawn(GameContext ctx);

    void spawn(GameContext ctx);

}
