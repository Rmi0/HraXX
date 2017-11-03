package com.java.miscik;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        /*UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        String[] options = new String[2];
        options[0] = "Console";
        options[1] = "GUI";
        int mode = JOptionPane.showOptionDialog(null,"Choose mode:","HraXX", 1,
                3,null, options, null);
        if (mode == 0) System.out.println("Console"); else if (mode == 1) System.out.println("GUI"); else System.exit(0);*/
        Game game = new Game();
        game.startGameLoop();

    }
}
