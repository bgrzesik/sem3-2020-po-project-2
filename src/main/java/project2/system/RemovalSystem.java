package project2.system;

import project2.GameContext;
import project2.entity.Entity;
import project2.listeners.RemoveListener;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class RemovalSystem implements GameSystem {

    private Set<RemoveListener> listeners = new HashSet<>();


    @Override
    public void tick(GameContext ctx) {
        Set<Entity> entities = ctx.getEntities();
        Set<Entity> pendingRemoval = entities
                .stream()
                .filter(Entity::pendingRemoval)
                .collect(Collectors.toSet());

        pendingRemoval.forEach(entity -> {
            this.listeners.forEach(l -> l.onRemove(ctx, entity));
            ctx.removeEntity(entity);
        });

    }

    public void addRemoveListener(RemoveListener listener) {
        this.listeners.add(listener);
    }

    public void removeRemoveListener(RemoveListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public boolean doesGameLogic() {
        return false;
    }
}
