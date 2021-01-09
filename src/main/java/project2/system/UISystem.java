package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import project2.GameContext;

public class UISystem implements GameSystem {

    private Stage stage;
    private Label score;

    public UISystem() {
        Skin skin = new Skin(Gdx.files.internal("libgdx-tests-ui/uiskin.json"),
                             new TextureAtlas("libgdx-tests-ui/uiskin.atlas"));

        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);

        Table table = new Table();
        table.setSkin(skin);
        table.setFillParent(true);

        this.score = new Label("Score: 0000", skin);

        table.add(score);
        table.top();

        stage.addActor(table);
    }



    @Override
    public void tick(GameContext ctx) {

        StatisticsSystem statisticsSystem = ctx.getSystem(StatisticsSystem.class);
        score.setText(String.format("Score: %4d", statisticsSystem.getScore()));

        this.stage.act();
        this.stage.draw();
    }

}
