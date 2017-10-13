package com.java.miscik.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Rolo on 5. 10. 2017.
 */
public class InvalidTileException extends Exception {
    @Override
    public void printStackTrace() {
        Logger.getAnonymousLogger().log(Level.WARNING,"Tile is invalid");
    }

    @Override
    public String getMessage() { return "Tile is invalid";}
}
