package project2.system;

import project2.GameContext;

import java.time.chrono.IsoChronology;

public class StatisticsSystem implements GameSystem {

    public int score = 0;


    @Override
    public void tick(GameContext ctx) {
    }


    public int getScore() {
        return score;
    }

    public void pickedUpCherry() {
        score += 50;
    }

}
