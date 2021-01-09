package project2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import project2.screens.GameplayScreen;
import project2.screens.MainMenuScreen;

public class Application extends Game {

    @Override
    public void create() {
//        setScreen(new GameplayScreen());
        setScreen(new MainMenuScreen());
    }
}