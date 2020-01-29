package example;

import ges.base.GESGameEngine;
import ges.graphics.GESRectangleShape;
import ges.graphics.GESVector2d;

import java.awt.*;

public class Horloge extends GESGameEngine {

    final int RADIUS = 200;

    GESVector2d center = new GESVector2d(400, 300);
    GESRectangleShape aiguille = new GESRectangleShape(4, RADIUS - 50);

    @Override
    protected void initialization() {
        setWindowHeight(600);
        setWindowWidth(800);

        aiguille.setBackgroundColor(Color.BLACK);
        aiguille.setPosition(center.createByTranslate(0, - aiguille.getAABB().getHeight()));
    }

    @Override
    protected void processEvent() {

    }

    @Override
    protected void updateGameLogic(double delta) {
        aiguille.rotate((Math.PI * 2 / 12) * delta, 2 + aiguille.getPosition().getX(),
                aiguille.getAABB().getHeight() + aiguille.getPosition().getY());
    }

    @Override
    protected void renderGame(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearWindow(g2d, new Color(99, 185, 81));

        g2d.setColor(Color.GRAY);
        g2d.fillOval((int)center.getX() - RADIUS - 20,
                (int)center.getY() - RADIUS - 20,
                (RADIUS+20)*2, (RADIUS+20)*2);

        g2d.setColor(Color.BLACK);
        for(int i = 1; i <= 12; i++){
            double x = center.getX() + RADIUS * Math.sin(i* Math.PI / 6);
            double y = center.getY() - RADIUS * Math.cos(i * Math.PI / 6);

            g2d.drawString(i+"", (int)x, (int)y);
        }

        aiguille.draw(g2d);
    }

    @Override
    protected void dispose() {

    }

    @Override
    protected void onWindowClosingCallback() {

    }
}
