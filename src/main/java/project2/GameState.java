package project2;

public enum GameState {
    PLAYING(true),
    PLAYER_PAUSE(false),
    DIED(false),
    WON(false),
    LOST(false),


    ;
    private boolean doLogic;

    GameState(boolean doLogic) {
        this.doLogic = doLogic;
    }

    public boolean doLogic() {
        return doLogic;
    }
}
