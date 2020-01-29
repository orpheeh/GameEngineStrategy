package ges.base;

import ges.base.event.GESKeyboardInput;
import ges.base.event.GESMouseInput;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public abstract class GESGameEngine implements Runnable {

    private final GESWindow window;
    private BufferStrategy strategy;

    private int windowWidth;
    private int windowHeight;
    protected  String gameName;
    protected Thread gameThread;
    protected volatile boolean runningLoop;
    protected GESKeyboardInput keyboardInput;
    protected GESMouseInput mouseInput;

    protected GESGameEngine(int windowWidth, int windowHeight, String gameName) {
        this.gameName = gameName;
        this.window = new GESWindow(windowWidth, windowHeight, gameName);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    protected GESGameEngine(){
        this(400, 400, "GES");
    }

    protected abstract void initialization();

    protected abstract void processEvent();

    protected abstract void updateGameLogic(double delta);

    protected abstract void renderGame(Graphics2D g2d);

    protected abstract void dispose();

    protected abstract void onWindowClosingCallback();

    protected void clearWindow(Graphics2D g2d, Color color){
        g2d.setColor(color);
        g2d.fillRect(0,0, windowWidth, windowHeight);
    }

    protected void setWindowWidth(int windowWidth){
        this.windowWidth = windowWidth;
        this.window.setWindowWidth(windowWidth);
    }

    protected void setWindowHeight(int windowHeight){
        this.windowHeight = windowHeight;
        this.window.setWindowHeight(windowHeight);
    }

    protected void setGameName(String gameName){
        this.gameName = gameName;
        this.window.setWindowTitle(gameName);
    }

    protected Thread getGameThread(){
        return gameThread;
    }

    public void startGameEngine() {
        initialization();

        window.createStrategyAndShowGUI();
        strategy = window.getBufferStrategy();

        Component component = window.getContentPane().getComponent(0);
        mouseInput = new GESMouseInput();
        component.addMouseListener(mouseInput);
        component.addMouseMotionListener(mouseInput);
        component.addMouseWheelListener(mouseInput);
        keyboardInput = new GESKeyboardInput();
        component.addKeyListener(keyboardInput);
        gameThread = new Thread(this);
        gameThread.start();
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public void run() {
        gameLoop();
    }

    private void gameLoop() {
        runningLoop = true;
        long lastTime = System.currentTimeMillis();
        long now;
        while (runningLoop){
            processEvent();

            now = System.currentTimeMillis();
            double elapsedTimeMillisecond = now - lastTime;
            lastTime = now;

            updateGameLogic(elapsedTimeMillisecond / 1000.0);
            drawDoubleBuffering();
        }

        dispose();
    }

    private void drawDoubleBuffering() {
        do {
            do {
                Graphics graphics = null;
                try {
                    graphics = strategy.getDrawGraphics();
                    renderGame((Graphics2D) graphics);
                } finally {
                    if (graphics != null) {
                        graphics.dispose();
                    }
                }
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
    }

    private void onWindowClosing(){
        try {
            runningLoop = false;
            gameThread.join();
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
        onWindowClosingCallback();
        System.exit(0);
    }
}
