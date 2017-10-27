package com.java.miscik;

/**
 * Created by Rolo on 5. 10. 2017.
 */
public class Tile {

    public static final int EMPTY = 0;
    public static final int PLAYER_A = 1;
    public static final int PLAYER_B = 2;

    private int state;

    public Tile() {
       this.state = EMPTY;
    }

    public Tile(int state) {
        if (!this.setState(state)) this.state = EMPTY;
    }

    public int getState() {
        return this.state;
    }

    public boolean setState(int state) {
        if (state != EMPTY && state != PLAYER_A && state != PLAYER_B) return false;
        this.state = state;
        return true;
    }

}
