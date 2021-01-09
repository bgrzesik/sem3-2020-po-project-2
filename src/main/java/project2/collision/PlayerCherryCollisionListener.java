package project2.collision;

import project2.GameContext;
import project2.entity.CherryEntity;
import project2.entity.Entity;
import project2.entity.PlayerEntity;
import project2.system.StatisticsSystem;

public class PlayerCherryCollisionListener implements PlayerCollisionListener {

    @Override
    public void collidedWith(GameContext ctx, PlayerEntity player, Entity entity) {
        if (!(entity instanceof CherryEntity)) {
            return;
        }

        CherryEntity cherry = (CherryEntity) entity;
        cherry.setAlive(false);

        StatisticsSystem statisticsSystem = ctx.getSystem(StatisticsSystem.class);
        statisticsSystem.pickedUpCherry();

    }

}
