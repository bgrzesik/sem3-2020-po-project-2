package project2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import project2.entity.Entity;
import project2.entity.EntityVisitor;
import project2.system.GameSystem;

import java.util.*;

public class GameContext {

    private final HashMap<Class<?>, GameSystem> systemsMap = new HashMap<>();
    private final List<GameSystem> systems = new ArrayList<>();
    private final Set<Entity> entities = new HashSet<>();
    private final AssetManager assets;
    private String mapFile;

    public GameContext(String mapFile) {
        this.mapFile = mapFile;
        this.assets = new AssetManager();

        this.assets.load(mapFile, Pixmap.class);
    }

    public <T extends GameSystem> T getSystem(Class<T> clazz) {
        return (T) systemsMap.get(clazz);
    }

    public List<GameSystem> getSystems(){
        return this.systems;
    }

    public AssetManager getAssets() {
        return this.assets;
    }

    public Set<Entity> getEntities() {
        return Collections.unmodifiableSet(this.entities);
    }

    public void registerSystem(GameSystem system) {
        this.systemsMap.put(system.getClass(), system);
        this.systems.add(system);
    }

    public void addEntity(Entity entity, Vector2 pos) {
        entity.added(this, pos);
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public String getMapFile() {
        return mapFile;
    }

    public void visit(EntityVisitor visitor) {
        for (Entity entity : entities) {
            entity.accept(visitor);
        }
    }
}
