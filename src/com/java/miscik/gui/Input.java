package com.java.miscik.gui;

import java.awt.event.*;

public class Input implements MouseListener, MouseMotionListener, KeyListener {
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

    //--- MOUSE MOTION LISTENER ----------------------------------------------------------------------------------------

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gui.mouseMoved(e);
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
