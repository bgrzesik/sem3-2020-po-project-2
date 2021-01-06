package project2;

import com.badlogic.gdx.Game;
import project2.screens.GameplayScreen;

public class Application extends Game {

    @Override
    public void create() {
        setScreen(new GameplayScreen());
    }
}