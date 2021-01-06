package project2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import project2.map.Map;
import project2.map.MapBody;
import project2.map.MapLoader;
import project2.map.MapRenderer;

public class GameplayScreen extends ScreenAdapter {

    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Body body;
    private AssetManager assets;
    private SpriteBatch spriteBatch;
    private Map map;
    private FrameBuffer mapFrameBuffer;

    @Override
    public void show() {
        this.assets = new AssetManager();
        this.spriteBatch = new SpriteBatch();
        this.assets.load("pacman.png", Texture.class);
        this.assets.load("assets.png", Texture.class);

        this.world = new World(new Vector2(0, 0), false);
        this.debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        this.assets.load("map.png", Pixmap.class);
        this.assets.finishLoadingAsset("map.png");
        this.assets.finishLoadingAsset("assets.png");

        this.map = new MapLoader(assets).loadMap("map.png");

        new MapBody(map).createBody(world);
        this.mapFrameBuffer = new MapRenderer(map).render(assets);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.map.getPlayerSpawnPoint());
        bodyDef.fixedRotation = true;

        this.body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setPosition(Vector2.Zero);
        circle.setRadius(0.45f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = circle;
        fixDef.density = 1;
        fixDef.restitution = 0;

        this.body.createFixture(fixDef);

    }

    @Override
    public void render(float dt) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        assets.update();
        Vector2 speed = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speed.y += 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speed.y -= 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speed.x -= 1.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speed.x += 1.0f;
        }

        speed = speed.nor();
        speed.scl(5f);

        // f = mdv/t
        Vector2 f = speed.sub(body.getLinearVelocity())
                         .scl(body.getMass());

        body.applyLinearImpulse(f, Vector2.Zero, true);

        Vector2 playerPos = body.getWorldCenter().sub(0.5f, 0.5f);

        float scale = 2.4e-2f;
        Matrix4 proj = new Matrix4();
        float width = screenWidth * scale;
        float height = screenHeight * scale;
        proj.setToOrtho2D(0, 0, width, height, -0.1f, 1.0f);
        proj.translate(width / 2.0f - playerPos.x, height / 2.0f - playerPos.y, 0.0f);


        world.step(dt, 8, 3);


        if (assets.isLoaded("assets.png")) {
            Texture texture = assets.get("assets.png");
            TextureRegion region = new TextureRegion(texture, 0, 0, 128, 128);


            spriteBatch.setProjectionMatrix(proj);
            spriteBatch.begin();

            Texture mapTexture = mapFrameBuffer.getColorBufferTexture();
            spriteBatch.draw(region, playerPos.x, playerPos.y, 1.0f, 1.0f);
            spriteBatch.draw(mapTexture, -0.5f, -0.5f, map.getSizeX(), map.getSizeY());
            spriteBatch.end();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F3)) {
            debugRenderer.render(world, proj);
        }
    }
}
