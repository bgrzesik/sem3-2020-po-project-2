package project2.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import project2.GameContext;
import project2.map.GameMap;
import project2.system.PhysicsSystem;

public class MapEntity extends AbstractEntity {

    private static final float OFFSET = 0.45f;
    private static final float SMALL_OFFSET = (0.5f - OFFSET) * 4f;
    private GameMap map;

    public MapEntity(GameMap map) {
        this.map = map;
    }

    @Override
    public Body createBody(PhysicsSystem physicsSystem, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        this.body = physicsSystem.getWorld().createBody(bodyDef);
        body.setUserData(this);

        // RIGHT
        for (int x = 0; x < map.getSizeX() - 1; x++) {
            for (int y = 0; y < map.getSizeY(); y++) {
                while (y < map.getSizeY() && !map.isWall(x, y)) {
                    y++;
                }

                int startY = y;

                while (y < map.getSizeY() && map.isWall(x, y) && !map.isWall(x + 1, y)) {
                    y++;
                }

                int endY = y;

                if (startY == endY) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                Vector2 start = new Vector2(x + OFFSET, startY - OFFSET);
                Vector2 end = new Vector2(x + OFFSET, endY - 1.0f + OFFSET);

                if (startY < 1 || map.isWall(x, startY - 1)) {
                    start.y -= SMALL_OFFSET;
                }

                if (endY > map.getSizeY() - 1  || map.isWall(x, endY)) {
                    end.y += SMALL_OFFSET;
                }

                polygonShape.set(start, end);
                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }

        // LEFT
        for (int x = 1; x < map.getSizeX(); x++) {
            for (int y = 0; y < map.getSizeY(); y++) {
                while (y < map.getSizeY() && !map.isWall(x, y)) {
                    y++;
                }

                int startY = y;

                while (y < map.getSizeY() && map.isWall(x, y) && !map.isWall(x - 1, y)) {
                    y++;
                }

                int endY = y;

                if (startY == endY) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                Vector2 start = new Vector2(x - OFFSET, startY - OFFSET);
                Vector2 end = new Vector2(x - OFFSET, endY - 1.0f + OFFSET);

                if (startY < 1 || map.isWall(x, startY - 1)) {
                    start.y -= SMALL_OFFSET;
                }

                if (endY > map.getSizeY() - 1 || map.isWall(x, endY)) {
                    end.y += SMALL_OFFSET;
                }

                polygonShape.set(start, end);

                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }


        // UP
        for (int y = 0; y < map.getSizeY() - 1; y++) {
            for (int x = 0; x < map.getSizeX(); x++) {
                while (x < map.getSizeX() && !map.isWall(x, y)) {
                    x++;
                }

                int startX = x;

                while (x < map.getSizeX() && map.isWall(x, y) && !map.isWall(x, y + 1)) {
                    x++;
                }

                int endX = x;

                if (startX == endX) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                Vector2 start = new Vector2(startX - OFFSET, y + OFFSET);
                Vector2 end = new Vector2(endX - 1.0f + OFFSET, y + OFFSET);
                polygonShape.set(start, end);

                if (startX < 1 || map.isWall(startX - 1, y)) {
                    start.x -= SMALL_OFFSET;
                }

                if (endX > map.getSizeY() - 1 || map.isWall(endX, y)) {
                    end.x += SMALL_OFFSET;
                }

                polygonShape.set(start, end);



                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }

        // DOWN
        for (int y = 1; y < map.getSizeY(); y++) {
            for (int x = 0; x < map.getSizeX(); x++) {
                while (x < map.getSizeX() && !map.isWall(x, y)) {
                    x++;
                }

                int startX = x;

                while (x < map.getSizeX() && map.isWall(x, y) && !map.isWall(x, y - 1)) {
                    x++;
                }

                int endX = x;

                if (startX == endX) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                Vector2 start = new Vector2(startX - OFFSET, y - OFFSET);
                Vector2 end = new Vector2(endX - 1.0f + OFFSET, y - OFFSET);

                if (startX < 1 || map.isWall(startX - 1, y)) {
                    start.x -= SMALL_OFFSET;
                }

                if (endX > map.getSizeY() - 1 || map.isWall(endX, y)) {
                    end.x += SMALL_OFFSET;
                }

                polygonShape.set(start, end);
                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }

        return body;
    }



    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visitMap(this);
    }

    public GameMap getMap() {
        return map;
    }
}
