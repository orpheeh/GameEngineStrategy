package ges.base;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GESWindow  extends JFrame {

    int windowWidth;
    int windowHeight;
    String windowTitle;
    final Canvas canvas;

    BufferStrategy strategy = null;

    public GESWindow(int windowWidth, int windowHeight, String windowTitle) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowTitle = windowTitle;
        this.canvas = new Canvas();
    }

    public void createStrategyAndShowGUI(){
        setTitle(windowTitle);
        setResizable(false);
        canvas.setSize(new Dimension(windowWidth, windowHeight));
        canvas.requestFocus();
        getContentPane().add(canvas);
        pack();
        setVisible(true);
        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
    }

    public BufferStrategy getBufferStrategy(){
        return strategy;
    }
}
