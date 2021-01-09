package project2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import project2.GameContext;
import project2.collision.PlayerCherryCollisionListener;
import project2.listeners.WorldRemoveListener;
import project2.system.*;

public class GameplayScreen extends ScreenAdapter {

    private GameContext ctx;
    private String mapFile;

    public GameplayScreen(String mapFile) {
        this.mapFile = mapFile;
    }


    @Override
    public void show() {
        ctx = new GameContext(this.mapFile);

        ctx.registerSystem(new MapSystem());

        ctx.registerSystem(new PlayerMovementSystem());
        ctx.registerSystem(new PlayerCollisionSystem());

        ctx.registerSystem(new PhysicsSystem());
        ctx.registerSystem(new RemovalSystem());
        ctx.registerSystem(new StatisticsSystem());

        ctx.registerSystem(new RenderingSystem());
        ctx.registerSystem(new UISystem());


        RemovalSystem removalSystem = ctx.getSystem(RemovalSystem.class);
        removalSystem.addRemoveListener(new WorldRemoveListener());


        PlayerCollisionSystem collisionSystem = ctx.getSystem(PlayerCollisionSystem.class);
        collisionSystem.addCollisionListener(new PlayerCherryCollisionListener());
    }

    @Override
    public void render(float dt) {
        ctx.getAssets().update();

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (GameSystem system : ctx.getSystems()) {
            system.tick(ctx);
        }
    }
}
