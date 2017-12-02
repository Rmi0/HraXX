package com.java.miscik.gui;

import com.java.miscik.Game;
import com.java.miscik.ImageReader;
import com.java.miscik.Tile;
import com.java.miscik.exceptions.InvalidTileException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public class GUIGame extends Canvas implements Runnable {

    //SETTINGS
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;
    public static final String TITLE = "HraXX";
    //END OF SETTINGS
    public static final int WIDTH=TILE_WIDTH*8+32, HEIGHT=TILE_HEIGHT*8;
    private Game game;
    private Thread thread;
    private boolean running;

    private boolean renderInfo;
    private int mouseX, mouseY;

    public GUIGame(Game game) {
        this.game = game;
        this.running = false;
        this.thread = null;

        this.renderInfo = false;
        this.mouseX = 0;
        this.mouseY = 0;

        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH,HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH,HEIGHT));

        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        Input input = new Input(this);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        this.addKeyListener(input);
        this.start();
    }

    @Override
    public void run() {
        this.running = true;
        while (running) {
            tick();
            render();
        }
        stop();
    }

    public synchronized void start() {
        this.thread = new Thread(this);
        thread.start();
        this.running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            this.running = false;
        } catch (Exception ex) {ex.printStackTrace();}
    }

    private void tick() {
        int blueTiles = game.getGameField().getTileCount(Tile.PLAYER_A);
        int redTiles = game.getGameField().getTileCount(Tile.PLAYER_B);
        if (game.getGameField().isFilled() || blueTiles == 0 || redTiles == 0) return;
        if (!game.getGameField().isFilled()) {
            int opposingPlayer = game.getPlr() == Tile.PLAYER_A ? Tile.PLAYER_B : Tile.PLAYER_A;
            if (!game.isMovementPossible()) game.setPlr(opposingPlayer);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //--------------------------------------------------------------------------------------------------------------
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);
        g.setColor(new Color(204,179,41));
        g.fillRect(WIDTH-32,0,32,HEIGHT);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                g.setColor(Color.BLACK);
                try {
                    int tile = game.getGameField().read(x, y);
                    if (tile == Tile.PLAYER_A) g.setColor(new Color(35,25,170));
                    else if (tile == Tile.PLAYER_B) g.setColor(new Color(170,25,35));
                    else g.setColor(Color.GRAY);
                } catch (Exception ex) {ex.printStackTrace();}
                g.fillRect(x*TILE_WIDTH,y*TILE_HEIGHT,TILE_WIDTH,TILE_HEIGHT);
                g.setColor(new Color(0,0,0,100));
                g.drawRect(x*TILE_WIDTH,y*TILE_HEIGHT,TILE_WIDTH,TILE_HEIGHT);
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int blueTiles = game.getGameField().getTileCount(Tile.PLAYER_A);
        int redTiles = game.getGameField().getTileCount(Tile.PLAYER_B);

        //---------------------------------------------------------
        //RENDER CURRENT PLAYER -----------------------------------
        g2d.setColor(game.getPlr()==Tile.PLAYER_A? new Color(35,25,170) : new Color(170,25,35));
        g2d.setFont(g.getFont().deriveFont(32f));
        FontMetrics fm = g2d.getFontMetrics();
        String text = game.getPlr()==Tile.PLAYER_A?"A":"B";
        g2d.drawString(text,(WIDTH-16-fm.stringWidth(text)/2),32);
        //---------------------------------------------------------
        //RENDER OPTIONS ------------------------------------------
        g.drawImage(ImageReader.readImg("undo.png"),WIDTH-31,HEIGHT-32,null);
        g.drawImage(ImageReader.readImg("clear.png"),WIDTH-31,HEIGHT-64,null);
        if (mouseX >= WIDTH-32) {
            //UNDO
            if (mouseY >= HEIGHT-32) {
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(WIDTH - 132, HEIGHT - 32, 100, 32);
                g.setColor(Color.BLACK);
                g.getFont().deriveFont(16f);
                g.drawString("UNDO", WIDTH - 127, HEIGHT - 5);
            } else if (mouseY >= HEIGHT-64) {
            //RESTART
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(WIDTH - 132, HEIGHT - 64, 100, 32);
                g.setColor(Color.BLACK);
                g.getFont().deriveFont(16f);
                g.drawString("RESET", WIDTH - 127, HEIGHT - 37);
            }
        }

        //---------------------------------------------------------
        //RENDER INFORMATION --------------------------------------
        if (renderInfo) {
            int scale = 5;
            int offset = 5;

            double bluePercent = Math.ceil(blueTiles*100/(blueTiles+redTiles));
            double redPercent = 100 - bluePercent;

            g.setColor(new Color(0,0,0,175));
            g.fillRect(0,0,WIDTH,HEIGHT);

            g.setColor(new Color(35, 25, 170));
            g.fillRect(offset, HEIGHT - 50 - offset, (int)bluePercent * scale, 50);
            g.setColor(new Color(170, 25, 35));
            g.fillRect(offset + (int)bluePercent * scale, HEIGHT - 50 - offset, (int)redPercent * scale, 50);
        }
        //---------------------------------------------------------
        //RENDER WINNER -------------------------------------------
        if (game.getGameField().isFilled() || blueTiles == 0 || redTiles == 0) {
            if (blueTiles == redTiles) text = "IT'S A TIE!";
            else if (blueTiles > redTiles) text = "PLAYER A WON!";
            else text = "PLAYER B WON!";
            g2d.setFont(g.getFont().deriveFont(64f));
            g2d.setColor(Color.GREEN);
            g2d.drawString(text,(WIDTH-32)/2-fm.stringWidth(text),HEIGHT/2+16);
        }
        //--------------------------------------------------------------------------------------------------------------
        g2d.dispose();
        g.dispose();
        bs.show();
    }

    public void mousePressed(MouseEvent e) {
        int blueTiles = game.getGameField().getTileCount(Tile.PLAYER_A);
        int redTiles = game.getGameField().getTileCount(Tile.PLAYER_B);
        if (game.getGameField().isFilled() || blueTiles == 0 || redTiles == 0) return;

        if (mouseX > WIDTH-32) {
            //UNDO
            if (mouseY >= HEIGHT-32) game.undo();
            //RESTART
            if (mouseY >= HEIGHT-64 && mouseY < HEIGHT-32) {
                game.getGameField().initField();
                game.setPlr(Tile.PLAYER_A);
            }
            return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX()/TILE_WIDTH;
            int y = e.getY()/TILE_HEIGHT;
            try {
                game.move(""+(char)(y+'A')+(x+1));
            }catch (InvalidTileException ex) {ex.printStackTrace();}
        }
    }

    public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }

    public void keyPressed(KeyEvent e) {
        //RESTART
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
            game.getGameField().initField();
            game.setPlr(Tile.PLAYER_A);
        }
        //UNDO
        if (e.getKeyChar() == 'b' || e.getKeyChar() == 'B') {
            game.undo();
        }

        if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') {
            this.renderInfo = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') {
            this.renderInfo = false;
        }
    }

}
