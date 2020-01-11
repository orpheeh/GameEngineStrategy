package ges.base.event;

import ges.graphics.GESVector2d;

import java.awt.*;
import java.awt.event.*;

public class GESMouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final int BUTTON_COUNT = 3;
    private final int[] buttons = new int[BUTTON_COUNT];
    private final boolean[] buttonPolled = new boolean[BUTTON_COUNT];

    private Point mousePos;
    private Point currentPos;

    public GESMouseInput(){
        mousePos = new Point(0, 0);
        currentPos = new Point(0, 0);
    }

    public synchronized boolean buttonDown(int button){
        return buttons[button] > 0;
    }

    public synchronized boolean buttonDownOnce(int button){
        return buttons[button] == 1;
    }

    public Point getMousePosition(){
        return mousePos;
    }

    public void poll(){
        mousePos = currentPos;

        for(int i = 0; i < buttonPolled.length; i++){
            if(buttonPolled[i]){
                buttons[i]++;
            } else {
                buttons[i] = 0;
            }
        }
    }

    @Override
    public synchronized void mouseClicked(MouseEvent e) {

    }

    @Override
    public synchronized void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if(button >= 0 && button < BUTTON_COUNT){
            buttonPolled[button] = true;
        }
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if(button >= 0 && button < BUTTON_COUNT){
            buttonPolled[button] = false;
        }
    }

    @Override
    public synchronized void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        currentPos = e.getPoint();
    }

    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {

    }
}
