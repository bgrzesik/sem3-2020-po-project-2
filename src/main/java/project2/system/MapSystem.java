package project2.system;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import project2.GameContext;
import project2.entity.MapEntity;
import project2.map.GameMap;
import project2.spawn.Spawner;

public class MapSystem implements GameSystem {

    private MapEntity mapEntity = null;
    private GameMap map;


    @Override
    public void tick(GameContext ctx) {
        if (mapEntity == null) {
            AssetManager assets = ctx.getAssets();
            String mapFile = ctx.getMapFile();

            if (!assets.isLoaded(mapFile)) {
                return;
            }

            this.map = new GameMap(assets, mapFile);
            this.mapEntity = new MapEntity(map);

            ctx.addEntity(mapEntity, Vector2.Zero);
        }


        for (Spawner spawner : map.getSpawners()) {
            if (spawner.willSpawn(ctx)) {
                spawner.spawn(ctx);
            }
        }
    }

    public GameMap getMap() {
        return map;
    }

    public void reset() {
        for (Spawner spawner : map.getSpawners()) {
           spawner.reset();
        }
    }
}
