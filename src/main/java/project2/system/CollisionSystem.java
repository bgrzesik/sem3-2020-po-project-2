package project2.system;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import project2.GameContext;
import project2.entity.Entity;
import project2.entity.MapEntity;
import project2.entity.PlayerEntity;
import project2.collision.PlayerCollisionListener;

import java.util.HashSet;
import java.util.Set;

public class CollisionSystem implements GameSystem{

    private final Set<PlayerCollisionListener> listeners = new HashSet<>();

    @Override
    public void tick(GameContext ctx) {
        PhysicsSystem physicsSystem = ctx.getSystem(PhysicsSystem.class);
        World world = physicsSystem.getWorld();


        Array<Contact> contacts = world.getContactList();

        for (Contact contact : contacts) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();


            if (entityA instanceof PlayerEntity && !(entityB instanceof MapEntity)) {
                this.listeners.forEach(listener -> {
                   listener.collidedWith(ctx, (PlayerEntity) entityA, entityB);
                });
            } else if (entityB instanceof PlayerEntity && !(entityA instanceof MapEntity)) {
                this.listeners.forEach(listener -> {
                    listener.collidedWith(ctx, (PlayerEntity) entityB, entityA);
                });
            }
        }

    }

    public void addCollisionListener(PlayerCollisionListener listener) {
        this.listeners.add(listener);
    }

    public void removeCollisionListener(PlayerCollisionListener listener) {
        this.listeners.remove(listener);
    }

}
