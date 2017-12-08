package com.java.miscik;

import java.io.*;

/**
 * Created by client on 08.12.2017.
 */
public class GamesaveManager {

    private static GamesaveManager instance = null;

    public static GamesaveManager getInstance() {
        if (instance == null) instance = new GamesaveManager();
        return instance;
    }

    private GamesaveManager() {}

    public void save(Game game, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(game);

            oos.close();
            fos.close();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public Game load(String path) {
        Game game = null;
        try {
            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);

                game = (Game) ois.readObject();

                ois.close();
                fis.close();
            }
        } catch (Exception ex) {ex.printStackTrace();}
        return game;
    }

}
