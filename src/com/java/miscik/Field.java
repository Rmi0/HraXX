package com.java.miscik;

import com.java.miscik.exceptions.InvalidTileException;

import java.awt.*;

/**
 * Created by Rolo on 5. 10. 2017.
 */
public class Field {

    private Tile[][] gameField;

    public Field() {
        this.gameField = new Tile[8][8];
        initField();
    }

    public void initField() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.gameField[x][y] = new Tile();
            }
        }

        this.gameField[3][3] = new Tile(Tile.PLAYER_A);
        this.gameField[4][4] = new Tile(Tile.PLAYER_A);
        this.gameField[3][4] = new Tile(Tile.PLAYER_B);
        this.gameField[4][3] = new Tile(Tile.PLAYER_B);
    }

    public boolean write(int x, int y, int state) throws InvalidTileException {
        if (x > 7 || y > 7 || x < 0 || y < 0)  throw new InvalidTileException();
        return this.gameField[x][y].setState(state);
    }

    public int read(int x, int y) throws InvalidTileException {
        if (x > 7 || y > 7 || x < 0 || y < 0) throw new InvalidTileException();
        return this.gameField[x][y].getState();
    }

    public boolean isFilled() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (this.gameField[x][y].getState() == Tile.EMPTY) return false;
            }
        }
        return true;
    }

    public int getTileCount(int tile) {
        int count = 0;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (this.gameField[x][y].getState() == tile) count++;
            }
        }

        return count;
    }

    public void printField() {
        System.out.println("");
        System.out.println("    1 2 3 4 5 6 7 8");
        System.out.println();
        try {
            for (int y = 0; y < 8; y++) {
                System.out.print((char)(y+'A')+"   ");
                for (int x = 0; x < 8; x++) {
                    if (read(x,y) == Tile.PLAYER_A) System.out.print("A ");
                    if (read(x,y) == Tile.PLAYER_B) System.out.print("B ");
                    if (read(x,y) == Tile.EMPTY) System.out.print("0 ");
                    if (x == 7) System.out.println();
                }
            }
        } catch (InvalidTileException ex) {
            ex.printStackTrace();
        }
    }

}
