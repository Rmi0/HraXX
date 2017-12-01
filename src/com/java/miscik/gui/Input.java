package com.java.miscik.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements MouseListener, KeyListener {
    private GUIGame gui;

    public Input(GUIGame gui) {
        this.gui = gui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        gui.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    //---- KEY LISTENER ------------------------------------------------------------------------------------------------


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gui.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gui.keyReleased(e);
    }
}
