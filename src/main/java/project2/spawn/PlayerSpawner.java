package project2.spawn;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.PlayerEntity;

public class PlayerSpawner extends AbstractSpawner {

    private PlayerEntity player;
    private int lives = 3;

    public PlayerSpawner(GridPoint2 pos) {
        super(pos);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        return player == null || !player.isAlive();
    }

    @Override
    public void spawn(GameContext ctx) {
        this.player = new PlayerEntity(this.lives);
        ctx.addEntity(player, new Vector2(this.pos.x, this.pos.y));
    }

    @Override
    public void reset() {
        this.lives = this.player.getLives();
        this.player = null;
    }
}
