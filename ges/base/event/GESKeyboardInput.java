package ges.base.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GESKeyboardInput implements KeyListener {

    private final int KEY_COUNT = 256;
    private final int[] keys = new int[KEY_COUNT];
    private final boolean[] polled = new boolean[KEY_COUNT];

    public synchronized boolean keyDown(int keyCode){
        return keys[keyCode] > 0;
    }

    public synchronized boolean keyDownOnce(int keyCode){
        return keys[keyCode] == 1;
    }

    public void poll(){
        for(int i = 0; i < polled.length; i++){
            if(polled[i]){
                keys[i]++;
            } else {
                keys[i] = 0;
            }
        }
    }

    @Override
    public synchronized void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < KEY_COUNT){
            polled[keyCode] = true;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode >= 0 && keyCode < KEY_COUNT){
            polled[keyCode] = false;
        }
    }
}
