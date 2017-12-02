package com.java.miscik;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageReader {

    public static BufferedImage readImg(String name) {
        try {
            return ImageIO.read(Main.class.getResource("res/"+name));
        } catch (IOException ex) {ex.printStackTrace();}
        return null;
    }

}
