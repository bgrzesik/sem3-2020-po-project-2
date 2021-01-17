package project2.system;

import project2.GameContext;
import project2.GameState;
import project2.map.GameMap;

import java.time.chrono.IsoChronology;

public class StatisticsSystem implements GameSystem {

    public int score = 0;
    public int points = 0;


    @Override
    public void tick(GameContext ctx) {
        GameMap map = ctx.getSystem(MapSystem.class).getMap();

        if (map != null && points >= map.getTotalPoints()) {
            StateSystem state = ctx.getSystem(StateSystem.class);
            state.setGameState(GameState.WON);
        }
    }

    public int getScore() {
        return score;
    }

    public void pickedUpCherry() {
        score += 50;
    }

    public void pickedUpPoint() {
        score += 5;
        points += 1;
    }

    public void ateEnemy() {
        score += 50;
    }
}
