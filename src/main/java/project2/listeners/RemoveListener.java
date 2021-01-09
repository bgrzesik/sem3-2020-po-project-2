package project2.listeners;

import project2.GameContext;
import project2.entity.Entity;

public interface RemoveListener {

    void onRemove(GameContext ctx, Entity entity);

}
