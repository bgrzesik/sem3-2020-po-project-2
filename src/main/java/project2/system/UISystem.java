package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import project2.Application;
import project2.GameContext;
import project2.GameState;
import project2.entity.PlayerEntity;
import project2.screens.MainMenuScreen;

public class UISystem implements GameSystem {

    private final Label score;

    private final Stage overlayStage;
    private final Stage pauseStage;
    private final Stage wonStage;
    private final Stage lostStage;
    private final Stage diedStage;

    private GameContext ctx = null;

    public UISystem() {
        Skin skin = new Skin(Gdx.files.internal("libgdx-tests-ui/uiskin.json"),
                             new TextureAtlas("libgdx-tests-ui/uiskin.atlas"));

        this.overlayStage = new Stage(new ScreenViewport());

        Table table = new Table(skin);
        table.setFillParent(true);
        this.overlayStage.addActor(table);


        Table table1 = new Table(skin);
        table1.setBackground("blue");

        this.score = new Label("", skin);
        table1.add(this.score).pad(10);

        table.add(table1);
        table.top();

        this.pauseStage = new Stage(new ScreenViewport());

        table = new Table(skin);
        table.setFillParent(true);
        this.pauseStage.addActor(table);

        TextButton resume = new TextButton("Resume", skin);
        table.add(resume).pad(10).row();

        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StateSystem state = ctx.getSystem(StateSystem.class);
                state.setGameState(GameState.PLAYING);
            }
        });

        table.add(createExitButton(skin)).row();


        this.wonStage = new Stage();
        table = new Table(skin);
        table.setFillParent(true);
        table1 = new Table(skin);
        table1.setBackground("blue");
        table.add(table1);
        table1.add("You won!").pad(10).row();
        table1.add(createExitButton(skin)).pad(10).row();;
        this.wonStage.addActor(table);

        this.lostStage = new Stage();
        table = new Table(skin);
        table.setFillParent(true);
        table1 = new Table(skin);
        table1.setBackground("blue");
        table.add(table1);
        table1.add("You lost!").pad(10).row();
        table1.add(createExitButton(skin)).pad(10).row();;
        this.lostStage.addActor(table);

        this.diedStage = new Stage();
        table = new Table(skin);
        table.setFillParent(true);
        table1 = new Table(skin);
        table1.setBackground("blue");
        table.add(table1);
        table1.add("You died!").pad(10).row();
        this.diedStage.addActor(table);
    }

    private TextButton createExitButton(Skin skin) {
        TextButton exit = new TextButton("Exit to main menu", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Application app = (Application) Gdx.app.getApplicationListener();
                app.setScreen(new MainMenuScreen());
            }
        });
        return exit;
    }


    @Override
    public void tick(GameContext ctx) {
        this.ctx = ctx;

        PlayerEntity player = ctx.getSystem(PlayerSystem.class).getPlayer();
        StatisticsSystem statisticsSystem = ctx.getSystem(StatisticsSystem.class);

        int fps = Gdx.graphics.getFramesPerSecond();
        int lives = player != null ? player.getLives() : 0;
        int score = statisticsSystem.getScore();

        String ui = String.format("FPS: %d\nScore: %4d\nLives: %d",
                                  fps, score, lives);
        this.score.setText(ui);

        StateSystem state = ctx.getSystem(StateSystem.class);

        drawStage(this.overlayStage);
        switch (state.getGameState()) {
            case PLAYER_PAUSE:
                drawStage(this.pauseStage);
                break;

            case DIED:
                drawStage(this.diedStage);
                break;

            case WON:
                drawStage(this.wonStage);
                break;

            case LOST:
                drawStage(this.lostStage);
                break;
        }
    }

    public static void drawStage(Stage stage) {
        Gdx.input.setInputProcessor(stage);
        stage.getViewport()
             .update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.act();
        stage.draw();
    }

    @Override
    public boolean doesGameLogic() {
        return false;
    }

}
