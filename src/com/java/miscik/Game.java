package com.java.miscik;

import com.java.miscik.exceptions.InvalidTileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rolo on 5. 10. 2017.
 */
public class Game {

    private int plr;
    private Field gameField;
    private List<Tile> lastMove;
    private Tile lastSelected;
    private int lastPlr;

    public Game() {
        this.plr = Tile.PLAYER_A;
        this.gameField = new Field();
        this.lastMove = new ArrayList<>();
        this.lastSelected = null;
        this.lastPlr = 0;
    }

    public void startGameLoop() throws InvalidTileException {
        Scanner sc = new Scanner(System.in);
        this.getGameField().printField();
        while (true) {
            System.out.println("IT'S PLAYER"+this.plr+"'S TURN!");
            if (!isMovementPossible()) {
                System.out.println("PLAYER"+this.plr+" IS NOT ABLE TO MOVE!");
                int opposingPlayer = this.plr == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;
                this.plr = opposingPlayer;
                continue;
            }
            String move = sc.nextLine();
            this.move(move);
            this.getGameField().printField();
            if (gameField.isFilled()) {
                if (gameField.getTileCount(1) > 32) {
                    System.out.println("PLAYER 1 WINS!");
                } else if (gameField.getTileCount(2) > 32) {
                    System.out.println("PLAYER 2 WINS!");
                } else {
                    System.out.println("IT'S A TIE!");
                }
                System.exit(0);
            }
        }
    }

    public void move(String tile) throws InvalidTileException {
        this.lastMove.removeAll(this.lastMove);
        String directions = getPossibleDirections(tile);
        //System.out.println(tile);
        if (directions == null) return;

        int tileX = Integer.parseInt(tile.substring(1))-1;
        int tileY = tile.toUpperCase().toCharArray()[0] - 'A';

        int opposingPlayer = this.plr == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;
        boolean wrote = false;

        for (int i = 0; i < directions.length(); i++) {
            int dir = directions.charAt(i)-48;
            if (writeToTiles(opposingPlayer,tileX,tileY,getXMovement(dir),getYMovement(dir))) {
                gameField.write(tileX,tileY,this.plr);
                this.lastSelected = gameField.getTile(tileX, tileY);
                this.lastPlr = this.plr;
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

        while (x <= 7 && y <= 7 && x >= 0 && y >= 0) {
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

        while (x <= 7 && y <= 7 && x >= 0 && y >= 0) {
            if (gameField.read(x,y) == opposingPlayer) {
                gameField.write(x,y,this.plr);
                this.lastMove.add(gameField.getTile(x,y));
                wrote = true;
            }
            else if (gameField.read(x,y) == plr)
                return wrote;
            else if (gameField.read(x,y) == Tile.EMPTY)
                return false;
            x+=dirX;
            y+=dirY;
        }

        return wrote;
    }

    public boolean isMovementPossible() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (getPossibleDirections(""+(char)(y+'A')+(x+1)) != null) return true;
            }
        }
        return false;
    }

    public void undo() {
        if (this.lastSelected == null || this.lastMove.size() == 0 || this.lastPlr == 0) return;
        for (Tile t : this.lastMove) {
            t.setState(this.plr);
        }
        this.lastSelected.setState(Tile.EMPTY);
        this.plr = this.lastPlr;

        this.lastPlr = 0;
        this.lastMove.removeAll(this.lastMove);
        this.lastSelected = null;
    }

    public Field getGameField() {
        return gameField;
    }

    public int getPlr() {
        return plr;
    }

    public void setPlr(int plr) {
        this.plr = plr;
    }
}
