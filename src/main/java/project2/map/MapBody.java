package project2.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MapBody {

    public static final float OFFSET = 0.45f;

    private final Map map;

    public MapBody(Map map) {
        this.map = map;
    }

    public Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);
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
                polygonShape.set(new Vector2(x + OFFSET, startY - OFFSET),
                                 new Vector2(x + OFFSET, endY - 1.0f + OFFSET));

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
                polygonShape.set(new Vector2(x - OFFSET, startY - OFFSET),
                                 new Vector2(x - OFFSET, endY - 1.0f + OFFSET));

                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }


        // UP
        for (int y = 0; y < map.getSizeX() - 1; y++) {
            for (int x = 0; x < map.getSizeY(); x++) {
                while (x < map.getSizeX() && !map.isWall(x, y)) {
                    x++;
                }

                int startX = x;

                while (x < map.getSizeY() && map.isWall(x, y) && !map.isWall(x, y + 1)) {
                    x++;
                }

                int endX = x;

                if (startX == endX) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                polygonShape.set(new Vector2(startX - OFFSET, y + OFFSET),
                                 new Vector2(endX - 1.0f + OFFSET, y + OFFSET));

                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }

        // DOWN
        for (int y = 1; y < map.getSizeX(); y++) {
            for (int x = 0; x < map.getSizeY(); x++) {
                while (x < map.getSizeX() && !map.isWall(x, y)) {
                    x++;
                }

                int startX = x;

                while (x < map.getSizeY() && map.isWall(x, y) && !map.isWall(x, y - 1)) {
                    x++;
                }

                int endX = x;

                if (startX == endX) {
                    continue;
                }

                EdgeShape polygonShape = new EdgeShape();
                polygonShape.set(new Vector2(startX - OFFSET, y - OFFSET),
                                 new Vector2(endX - 1.0f + OFFSET, y - OFFSET));

                FixtureDef def = new FixtureDef();
                def.shape = polygonShape;
                body.createFixture(def);
            }
        }

        return body;
    }
}
