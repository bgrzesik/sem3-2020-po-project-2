package project2.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import project2.GameContext;
import project2.GameState;

public class StateSystem implements GameSystem {
    private float countdown = -1f;
    private GameState gameState = GameState.PLAYING;

    @Override
    public void tick(GameContext ctx) {
        switch (gameState) {
            case PLAYING:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    this.gameState = GameState.PLAYER_PAUSE;
                }
                break;

            case PLAYER_PAUSE:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    this.gameState = GameState.PLAYING;
                }
                break;

            case WON:
            case LOST:
                break;

            case DIED:
                if (countdown == -1f) {
                    countdown = 5f;
                } else if (countdown > 0f){
                    countdown -= Gdx.graphics.getDeltaTime();
                } else {
                    countdown = -1f;
                    this.gameState = GameState.PLAYING;
                }
                break;

        }
    }

    @Override
    public boolean doesGameLogic() {
        return false;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
