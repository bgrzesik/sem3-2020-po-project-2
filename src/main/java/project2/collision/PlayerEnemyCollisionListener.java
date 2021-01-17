package project2.collision;

import project2.GameContext;
import project2.GameState;
import project2.entity.*;
import project2.system.MapSystem;
import project2.system.StateSystem;
import project2.system.StatisticsSystem;

public class PlayerEnemyCollisionListener implements PlayerCollisionListener {

    @Override
    public void collidedWith(GameContext ctx, PlayerEntity player, Entity entity) {
        if (!(entity instanceof EnemyEntity)) {
            return;
        }

        EnemyEntity enemy = (EnemyEntity) entity;

        if (player.isChasing()) {
            onEat(ctx, player, enemy);
        } else {
            onHit(ctx, player, enemy);
        }
    }


    public void onHit(GameContext ctx, PlayerEntity player, EnemyEntity enemy) {

        StateSystem state = ctx.getSystem(StateSystem.class);
        if (player.getLives() > 1) {
            player.died();
            state.setGameState(GameState.DIED);

            ctx.visit(new EntityVisitor() {
                @Override
                public void visitPlayer(PlayerEntity player) {
                    player.setAlive(false);
                }

                @Override
                public void visitEnemy(EnemyEntity enemy) {
                    enemy.setAlive(false);
                }
            });
        } else {
            state.setGameState(GameState.LOST);
        }

        ctx.getSystem(MapSystem.class).reset();
    }

    public void onEat(GameContext ctx, PlayerEntity player, EnemyEntity enemy) {
        enemy.setAlive(false);
        ctx.getSystem(StatisticsSystem.class).ateEnemy();
    }


}
