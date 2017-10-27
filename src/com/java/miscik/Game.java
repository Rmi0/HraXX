package com.java.miscik;

import com.java.miscik.exceptions.InvalidTileException;

import java.util.ArrayList;
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

    public void move(String tile) throws InvalidTileException {
        String directions = getPossibleDirections(tile);
        if (directions == null) return;

        int tileX = Integer.parseInt(tile.substring(1))-1;
        int tileY = tile.toUpperCase().toCharArray()[0] - 'A';

        int opposingPlayer = this.plr == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;
        boolean wrote = false;

        for (int i = 0; i < directions.length(); i++) {
            int dir = directions.charAt(i)-48;
            if (writeToTiles(opposingPlayer,tileX,tileY,getXMovement(dir),getYMovement(dir))) {
                gameField.write(tileX,tileY,this.plr);
                wrote = true;
            }
        }

        if (wrote) this.plr = opposingPlayer;
    }


    private int getXMovement(int direction) {
        switch (direction) {
            case 1: return -1;
            case 3: return 1;
            case 4: return -1;
            case 6: return 1;
            case 7: return -1;
            case 9: return 1;
        }
        return 0;
    }

    private int getYMovement(int direction) {
        switch (direction) {
            case 1: return 1;
            case 2: return 1;
            case 3: return 1;
            case 7: return -1;
            case 8: return -1;
            case 9: return -1;
        }
        return 0;
    }

    //
    public String getPossibleDirections(String tile) {
        String directions = "";

        if (tile.length() != 2) return null;
        if (!Character.isLetter(tile.toCharArray()[0]) || !Character.isDigit(tile.toCharArray()[1])) return null;
        int tileX = Integer.parseInt(tile.substring(1))-1;
        int tileY = tile.toUpperCase().toCharArray()[0] - 'A';

        if (tileX > 7 || tileY > 7 || tileX < 0 || tileY < 0) return null;
        try {
            if (gameField.read(tileX, tileY) != Tile.EMPTY) return null;

            int x = tileX;
            int y = tileY;
           // System.out.println(tileX+" | "+tileY);

            boolean canMove = false;
            int opposingPlayer = this.plr == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;

            if (tileX < 6) {
                if(checkProgression(opposingPlayer, x+1, y, 1, 0))
                    directions += "6";
            }
            if (tileX > 1) {
                if(checkProgression(opposingPlayer, x-1, y, -1, 0))
                    directions += "4";
            }
            if (tileY < 6) {
                if(checkProgression(opposingPlayer, x, y+1, 0, 1))
                    directions += "2";
            }
            if (tileY > 1) {
                if(checkProgression(opposingPlayer, x, y-1, 0, -1))
                    directions += "8";
            }
            ///////
            if (tileX < 6 && tileY < 6) {
                if(checkProgression(opposingPlayer, x+1, y+1, 1, 1))
                    directions += "3";
            }
            if (tileX > 1 && tileY < 6) {
                if(checkProgression(opposingPlayer, x-1, y+1, -1, 1))
                    directions += "1";
            }
            if (tileX > 1 && tileY > 1) {
                if(checkProgression(opposingPlayer, x-1, y-1, -1, -1))
                    directions += "7";
            }
            if (tileX < 6 && tileY > 1) {
                if(checkProgression(opposingPlayer, x+1, y-1, 1, -1))
                    directions += "9";
            }


        } catch (InvalidTileException ex) {
            ex.printStackTrace();
        }

        /////////////////////////////////
        return directions.equals("")?null:directions;
    }

    private boolean checkProgression(int opposingPlayer, int x, int y, int dirX, int dirY) throws InvalidTileException {
        int count=0;

        while (x < 7 && y < 7 && x > 0 && y > 0) {
            if (gameField.read(x,y) == opposingPlayer) count++;
            if (gameField.read(x,y) == plr)
                return count>0;
            if (gameField.read(x,y) == Tile.EMPTY)
                return false;
            x+=dirX;
            y+=dirY;
        }

        return false;
    }

    private boolean writeToTiles(int opposingPlayer, int x, int y, int dirX, int dirY) throws InvalidTileException {
        //if (!checkProgression(opposingPlayer,x,y,dirX,dirY)) return false;
        boolean wrote = false;

        x+=dirX;
        y+=dirY;

        while (x < 7 && y < 7 && x > 0 && y > 0) {
            if (gameField.read(x,y) == opposingPlayer) {
                gameField.write(x,y,this.plr);
                wrote = true;
            }
            if (gameField.read(x,y) == plr)
                return wrote;
            if (gameField.read(x,y) == Tile.EMPTY)
                return false;
            x+=dirY;
            y+=dirX;
        }

        return wrote;
    }

    public Field getGameField() {
        return gameField;
    }
}
