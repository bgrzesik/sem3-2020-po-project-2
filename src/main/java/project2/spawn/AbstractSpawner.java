package project2.spawn;

import project2.entity.Entity;

public abstract class AbstractSpawner implements Spawner {

    protected final int x;
    protected final int y;

    public AbstractSpawner(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getCoordX() {
        return this.x;
    }

    @Override
    public int getCoordY() {
        return this.y;
    }

}
