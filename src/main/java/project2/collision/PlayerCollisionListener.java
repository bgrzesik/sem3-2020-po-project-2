package project2.collision;

import project2.GameContext;
import project2.entity.EnemyEntity;
import project2.entity.Entity;
import project2.entity.PlayerEntity;

public interface PlayerCollisionListener {
    void collidedWith(GameContext ctx, PlayerEntity player, Entity entity);
}
