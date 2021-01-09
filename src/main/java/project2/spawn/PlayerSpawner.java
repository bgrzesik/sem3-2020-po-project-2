package project2.spawn;

import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.PlayerEntity;

public class PlayerSpawner extends AbstractSpawner {

    private PlayerEntity player;

    public PlayerSpawner(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean willSpawn(GameContext ctx) {
        return player == null;
    }

    @Override
    public void spawn(GameContext ctx) {
        this.player = new PlayerEntity();
        ctx.addEntity(player, new Vector2(x, y));
    }
}
