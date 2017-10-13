package com.java.miscik;

import com.java.miscik.exceptions.InvalidTileException;

/**
 * Created by Rolo on 5. 10. 2017.
 */
public class Game {

    private int plr;
    private Field gameField;

    public Game() {
        this.plr = Tile.PLAYER_A;
        this.gameField = new Field();
    }

    public boolean move(String tile) {
        if (tile.length() != 2) return false;
        if (!Character.isLetter(tile.toCharArray()[0]) || !Character.isDigit(tile.toCharArray()[1])) return false;
        int tileX = tile.toUpperCase().toCharArray()[0] - 'A';
        int tileY = Integer.parseInt(tile.substring(1));

        if (tileX > 8 || tileY > 8 || tileX < 0 || tileY < 0) return false;
        try {
            if (gameField.read(tileX, tileY) != Tile.EMPTY) return false;
        } catch (InvalidTileException ex) {ex.printStackTrace();}

        /////////////////////////////////
        return true;
    }
}
