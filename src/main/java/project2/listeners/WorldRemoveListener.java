package project2.listeners;

import project2.GameContext;
import project2.entity.Entity;
import project2.system.PhysicsSystem;

public class WorldRemoveListener  implements RemoveListener {
    @Override
    public void onRemove(GameContext ctx, Entity entity) {
        PhysicsSystem system = ctx.getSystem(PhysicsSystem.class);
        system.getWorld().destroyBody(entity.getBody());
    }
}
