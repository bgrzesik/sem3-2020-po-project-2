package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import project2.GameContext;
import project2.entity.*;
import project2.render.GenericRenderer;
import project2.render.MapRenderer;
import project2.render.Renderer;

public class RenderingSystem extends EntitySystem implements EntityVisitor {

    public static final String ASSETS_FILE = "assets.png";
    private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

    private final MapRenderer mapRenderer = new MapRenderer();

    private final GenericRenderer playerRenderer = new GenericRenderer(0, 0, new Vector2(1f, 1f));
    private final GenericRenderer enemyRenderer = new GenericRenderer(1, 0, new Vector2(1f, 1f));
    private final GenericRenderer cherryRenderer = new GenericRenderer(2, 0, new Vector2(1f, 1f));

    private TextureRegion[][] atlas = null;

    private SpriteBatch batch;

    @Override
    public EntityVisitor getVisitor() {
        return this;
    }

    @Override
    public void tick(GameContext ctx) {
        if (atlas == null) {
            AssetManager assets = ctx.getAssets();
            assets.load(ASSETS_FILE, Texture.class);

            if (assets.isLoaded(ASSETS_FILE)) {
                this.atlas = TextureRegion.split(assets.get(ASSETS_FILE), 128, 128);
            } else {
                return;
            }
        }

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.batch = new SpriteBatch();

        float scale = 2.4e-2f;
        Matrix4 proj = new Matrix4();
        float width = screenWidth * scale;
        float height = screenHeight * scale;
        proj.setToOrtho2D(0, 0, width, height, -0.1f, 1.0f);


        Vector2 playerPos = ctx.getSystem(PlayerMovementSystem.class).getPlayerPos();
        proj.translate(width / 2.0f - playerPos.x, height / 2.0f - playerPos.y, 0.0f);

        FrameBuffer.unbind();
        this.batch.setProjectionMatrix(proj);
        this.batch.begin();
        super.tick(ctx);
        this.batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.F3)) {
            World world = ctx.getSystem(PhysicsSystem.class).getWorld();
            debugRenderer.render(world, proj);
        }
    }

    public <E extends Entity> void renderWith(E entity, Renderer<E> renderer) {
        renderer.setEntity(entity);
        renderer.setRenderingSystem(this);
        renderer.render(this.batch);
    }

    @Override
    public void visitPlayer(PlayerEntity player) {
        renderWith(player, playerRenderer);
    }

    @Override
    public void visitEnemy(EnemyEntity enemy) {
        renderWith(enemy, enemyRenderer);
    }

    @Override
    public void visitCherry(CherryEntity cherry) {
        renderWith(cherry, cherryRenderer);
    }

    @Override
    public void visitMap(MapEntity map) {
        renderWith(map, mapRenderer);
    }

    public TextureRegion[][] getAtlas() {
        return atlas;
    }

}
