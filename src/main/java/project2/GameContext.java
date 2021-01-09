package project2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import project2.entity.Entity;
import project2.map.GameMap;
import project2.system.GameSystem;

import java.util.*;

public class GameContext {

    private final HashMap<Class<?>, GameSystem> systems = new HashMap<>();
    private final Set<Entity> entities = new HashSet<>();
    private final AssetManager assets;
    private String mapFile;

    public GameContext(String mapFile) {
        this.mapFile = mapFile;
        this.assets = new AssetManager();

        this.assets.load("assets.png", Texture.class);
        this.assets.load(mapFile, Pixmap.class);
    }

    public <T extends GameSystem> T getSystem(Class<T> clazz) {
        return (T) systems.get(clazz);
    }

    public Collection<GameSystem> getSystems(){
        return this.systems.values();
    }

    public AssetManager getAssets() {
        return this.assets;
    }

    public Set<Entity> getEntities() {
        return Collections.unmodifiableSet(this.entities);
    }

    public void registerSystem(GameSystem system) {
        this.systems.put(system.getClass(), system);
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
}
