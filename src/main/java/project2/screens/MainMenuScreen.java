package project2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import project2.Application;

public class MainMenuScreen extends ScreenAdapter {

    private Stage stage;

    @Override
    public void show() {
        Skin skin = new Skin(Gdx.files.internal("libgdx-tests-ui/uiskin.json"),
                             new TextureAtlas("libgdx-tests-ui/uiskin.atlas"));

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        Table table = new Table();
        table.setSkin(skin);
        table.setFillParent(true);


        SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("map.png", "map2.png");
        table.add(selectBox).pad(10).row();

        Button playMap1 = new TextButton("Play", skin);
        playMap1.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (playMap1.isChecked()) {
                    Application app = (Application) Gdx.app.getApplicationListener();
                    app.setScreen(new GameplayScreen(selectBox.getSelected()));
                }
            }
        });
        table.add(playMap1).pad(10).row();


        Button exit = new TextButton("Exit", skin);

        exit.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (exit.isChecked()) {
                    Gdx.app.exit();
                }
            }
        });
        table.add(exit).pad(10).row();

        this.stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
    }

}
