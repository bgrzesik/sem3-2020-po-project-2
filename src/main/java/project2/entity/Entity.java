package project2.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public interface Entity {

    Body getBody();

    void setAlive(boolean alive);
    boolean isAlive();

    void render(SpriteBatch batch);
    void update(float dt);

}
