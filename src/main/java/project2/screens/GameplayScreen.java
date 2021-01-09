package project2.screens;

import com.badlogic.gdx.ScreenAdapter;
import project2.GameContext;
import project2.system.*;

public class GameplayScreen extends ScreenAdapter {


    private GameContext ctx;

    @Override
    public void show() {
        ctx = new GameContext("map.png");

        ctx.registerSystem(new MapSystem());

        ctx.registerSystem(new PlayerSystem());

        ctx.registerSystem(new PhysicsSystem());
        ctx.registerSystem(new RemovalSystem());

        ctx.registerSystem(new RenderingSystem());
    }

    @Override
    public void render(float dt) {
        ctx.getAssets().update();

        for (GameSystem system : ctx.getSystems()) {
            system.tick(ctx);
        }
    }
}
