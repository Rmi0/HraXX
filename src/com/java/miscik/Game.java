package com.java.miscik;

import com.java.miscik.exceptions.InvalidTileException;

import java.util.List;

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

    public void move(String tile) {
        if (!canMove(tile)) return;


    }

    public boolean canMove(String tile) {
        if (tile.length() != 2) return false;
        if (!Character.isLetter(tile.toCharArray()[0]) || !Character.isDigit(tile.toCharArray()[1])) return false;
        int tileY = Integer.parseInt(tile.substring(1))-1;
        int tileX = tile.toUpperCase().toCharArray()[0] - 'A';

        if (tileX > 7 || tileY > 7 || tileX < 0 || tileY < 0) return false;
        try {
            if (gameField.read(tileX, tileY) != Tile.EMPTY) return false;

            int x = tileX;
            int y = tileY;
           // System.out.println(tileX+" | "+tileY);

            boolean canMove = false;
            int opposingPlayer = this.plr == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;

            if (tileX < 6) {
                if(checkProgression(opposingPlayer, x+1, y, 1, 0))
                    return true;
            }
            if (tileX > 1) {
                if(checkProgression(opposingPlayer, x-1, y, -1, 0))
                    return true;
            }
            if (tileY < 6) {
                if(checkProgression(opposingPlayer, x, y+1, 0, 1))
                    return true;
            }
            if (tileY > 1) {
                if(checkProgression(opposingPlayer, x, y-1, 0, -1))
                    return true;
            }
            ///////
            if (tileX < 6 && tileY < 6) {
                if(checkProgression(opposingPlayer, x+1, y+1, 1, 1))
                    return true;
            }
            if (tileX > 1 && tileY < 6) {
                if(checkProgression(opposingPlayer, x-1, y+1, -1, 1))
                    return true;
            }
            if (tileX > 1 && tileY > 1) {
                if(checkProgression(opposingPlayer, x-1, y-1, -1, -1))
                    return true;
            }
            if (tileX < 6 && tileY > 1) {
                if(checkProgression(opposingPlayer, x+1, y-1, 1, -1))
                    return true;
            }


        } catch (InvalidTileException ex) {
            ex.printStackTrace();
        }

        /////////////////////////////////
        return false;
    }

    private boolean checkProgression(int opposingPlayer, int x, int y, int dirX, int dirY) throws InvalidTileException {
        int count=0;
        while (x < 7 && y < 7 && x > 0 && y > 0) {
            if (gameField.read(x,y) == opposingPlayer) count++;
            if (gameField.read(x,y) == plr)
                return count>0;
            if (gameField.read(x,y) == Tile.EMPTY)
                return false;
            x=x+dirX;
            y=y+dirY;
        }

        return false;
    }

    private List getTilesToChange(int opposingPlayer, int x, int y, int dirX, int dirY) throws InvalidTileException {
        List<>

        while (x < 7 && y < 7 && x > 0 && y > 0) {
            if (gameField.read(x,y) == opposingPlayer) count++;
            if (gameField.read(x,y) == plr)
                return count>0;
            if (gameField.read(x,y) == Tile.EMPTY)
                return false;
            x=x+dirX;
            y=y+dirY;
        }

        return false;
    }
}
