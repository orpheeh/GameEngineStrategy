package example;

import ges.base.GESFramerate;
import ges.base.GESGameEngine;
import ges.graphics.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;


public class Game extends GESGameEngine {

    GESCircleShape balle;
    final double gravite = 100.0;
    GESVector2d vitesse;
    double acceleration;

    GESLabel timerLabel;
    GESFont font;
    double t;

    GESFramerate framerate = new GESFramerate();

    protected Game() {
    }

    @Override
    protected void initialization() {
        setWindowWidth(800);
        setWindowHeight(600);
        setGameName("Le nom du jeu");

        balle = new GESCircleShape(16);
        balle.setBackgroundColor(new Color(185, 12, 5));

        vitesse = new GESVector2d(0.0, 0.0);

        font = new GESFont();

        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResourceAsStream("font.ttf"));

            font.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 28));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timerLabel = new GESLabel(t + " s");
        timerLabel.setFont(font);
        timerLabel.setColor(Color.YELLOW);
        timerLabel.setPosition(400, 100);

        framerate.initialize();
    }

    @Override
    protected void processEvent() {
        keyboardInput.poll();

        acceleration = 0;

        if(keyboardInput.keyDown(KeyEvent.VK_RIGHT)){
            acceleration += 100;
        }
        if(keyboardInput.keyDown(KeyEvent.VK_LEFT)){
            acceleration -= 100;
        }
        if(keyboardInput.keyDown(KeyEvent.VK_SPACE)){
            vitesse.setY(-200);
        }
    }

    @Override
    protected void updateGameLogic(double delta) {
        framerate.calculate();

        vitesse.translate(acceleration * delta, gravite * delta);

        balle.move(vitesse.multiply(delta));

        if(balle.getPosition().getY() + balle.getAABB().getHeight() >= f(balle.getPosition().getX())){
            vitesse.setY(-0.89 * vitesse.getY());
        }

        if(balle.getPosition().getX() < 400 && balle.getPosition().getY() + balle.getAABB().getHeight() >= 400){
            vitesse.setX(vitesse.getX() * -0.75);
            balle.setPosition(400, balle.getPosition().getY());
        }
    }

    @Override
    protected void renderGame(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearWindow(g2d, Color.BLACK);

        g2d.setColor(Color.GRAY);

        g2d.fillRect(0, (int)f(0), 400, 600 - (int)f(0));
        g2d.fillRect(400, (int)f(400), 400,600 - (int)f(400));

        balle.draw(g2d);

        g2d.setColor(Color.WHITE);
        Random r = new Random();
        for(int i = 0; i < 100; i++){
            int x = r.nextInt(800);
            int y = r.nextInt(600);

            g2d.fillRect(x, y, 2, 2);
        }

        timerLabel.draw(g2d);

        g2d.setColor(Color.RED);
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        g2d.drawString(framerate.getFrameRate(), 100, 100);
    }

    @Override
    protected void dispose() {

    }

    @Override
    protected void onWindowClosingCallback() {

    }

    public static void main(String[] argc){
        GESGameEngine game = new Game();
        game.startGameEngine();
    }

    public double f(double x){
        if(x < 400){
            return 300;
        } else {
            return 500;
        }
    }
}
