package project2.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import project2.GameContext;

public interface Entity {

    void added(GameContext ctx, Vector2 position);

    Body getBody();

    void setAlive(boolean alive);

    boolean isAlive();

    Vector2 getPosition();

    void accept(EntityVisitor visitor);

    boolean pendingRemoval();

}
