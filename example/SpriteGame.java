package example;

import ges.base.GESGameEngine;
import ges.graphics.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SpriteGame extends GESGameEngine {
    GESSprite playerTop = new GESSprite();
    GESSprite playerBottom = new GESSprite();
    GESSprite background = new GESSprite();

    GESImage backgroundTex = new GESImage();
    GESImage playerTex = new GESImage();

    GESImageRect topImageRect = new GESImageRect(8, 4, 32, 32);
    GESImageRect bottomImageRect = new GESImageRect(128,48*32, 28, 18);
    GESVector2d playerVelocity = new GESVector2d();
    double acceleration = 0.0;

    Animation run;
    Animation idleTop, idleBottom;
    Animation idleUp;

    GESImageRect[] runData = new GESImageRect[]{
            new GESImageRect(252, 48*32, 24, 28),
            new GESImageRect(252+24, 48*32, 32, 28),
            new GESImageRect(252+24+32, 48*32, 32, 28),
            new GESImageRect(252+24+32*2, 48*32, 24, 28),
            new GESImageRect(252+24*2+32*2, 48*32, 22, 28),
            new GESImageRect(252+24*2+32*2+22, 48*32, 22, 28),
            new GESImageRect(252+24*2+32*2+22*2, 48*32, 24, 28),
            new GESImageRect(252+24*3+32*2+22*2, 48*32, 28, 28),
            new GESImageRect(252+24*3+32*2+22*2+28, 48*32, 32, 28),
            new GESImageRect(252+24*3+32*3+22*2+28, 48*32, 24, 28),
            new GESImageRect(252+24*4+32*3+22*2+28, 48*32, 18, 28),
            new GESImageRect(252+24*4+32*3+22*2+28+18, 48*32, 22, 28),
            new GESImageRect(252+24*4+32*3+22*3+28+18, 48*32, 28, 28),
            new GESImageRect(252+24*4+32*3+22*3+28*2+18, 48*32, 30, 28),
            new GESImageRect(252+24*4+32*3+22*3+28*2+18+30, 48*32, 20, 28),
            new GESImageRect(252+24*4+32*3+22*3+28*2+18+30+20, 48*32, 28, 28),
            new GESImageRect(252+24*4+32*3+22*3+28*3+18+30+20, 48*32, 30, 28),
            new GESImageRect(252+24*4+32*3+22*3+28*3+18+30*2+20, 48*32, 24, 28),
    };

    @Override
    protected void initialization() {
        setWindowWidth(516);
        setWindowHeight(300);
        setGameName("Metal Slug GES");

        try {
            playerTex.loadFromFile("player.gif");
            backgroundTex.loadFromFile("background.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        background.setImage(backgroundTex);
        background.setImageRect(new GESImageRect(0, 300, 516, 300));

        playerTop.setImage(playerTex);
        playerTop.setImageRect(topImageRect);

        playerTop.setPosition(50, 230);

        playerBottom.setImage(playerTex);
        playerBottom.setPosition(50, 230);
        playerBottom.setImageRect(bottomImageRect);

        playerBottom.move(new GESVector2d(0, 24));

        run = new Animation(18, 1.0);
        idleTop = new Animation(4, 0.75);
        idleBottom = new Animation(5, 0.75);
        idleUp = new Animation(4, 0.75);
        idleUp.stop();
        run.stop();
    }

    @Override
    protected void processEvent() {
        keyboardInput.poll();

        acceleration = 0;
        if(keyboardInput.keyDown(KeyEvent.VK_RIGHT)){
            run.start();
            acceleration += 100;
            playerTop.setDirection(GESSprite.Direction.LTR);
            playerBottom.setDirection(GESSprite.Direction.LTR);
        } else if(keyboardInput.keyDown(KeyEvent.VK_LEFT)){
            run.start();
            acceleration -= 100;
            playerTop.setDirection(GESSprite.Direction.RTL);
            playerBottom.setDirection(GESSprite.Direction.RTL);
        } else {
            run.stop();
        }

        if(keyboardInput.keyDown(KeyEvent.VK_UP)){
            idleUp.start();
        } else {
            idleUp.stop();
        }

        if(acceleration == 0){
            playerVelocity.setX(0);
        }
    }

    @Override
    protected void updateGameLogic(double v) {
        idleTop.update(v);
        idleBottom.update(v);
        idleUp.update(v);

        playerVelocity.setX(playerVelocity.getX() + acceleration * v);

        playerTop.move(new GESVector2d(playerVelocity.getX() * v, playerVelocity.getY() * v));
        playerBottom.move(new GESVector2d(playerVelocity.getX() * v, playerVelocity.getY() * v));

        playerTop.setImageRect(new GESImageRect(  12 + idleTop.currentFrame*32, 6, 32, 32));
        playerBottom.setImageRect(new GESImageRect(128 + idleBottom.currentFrame*24,48*32, 24, 18));

        run.update(v);

        if(idleUp.isStopped() == false){
            playerTop.setImageRect(new GESImageRect(  12 + idleUp.currentFrame*32, 6+32+20, 32, 32));
        }

        if(run.isStopped() == false){
            playerBottom.setImageRect(runData[run.currentFrame]);
        }
    }

    @Override
    protected void renderGame(Graphics2D graphics2D) {
        clearWindow(graphics2D, new Color(71, 95, 122));
        background.draw(graphics2D);
        playerBottom.draw(graphics2D);
        playerTop.draw(graphics2D);
    }

    @Override
    protected void dispose() {

    }

    @Override
    protected void onWindowClosingCallback() {

    }
}

class Animation {
    public int frameCount;
    public double animationDuration;
    public int currentFrame;
    public boolean isStopped;
    public double elapsedTime = 0;

    public Animation(int frameCount, double animationDuration){
        this.frameCount = frameCount;
        this.animationDuration = animationDuration;
    }

    public void stop(){
        isStopped = true;
        elapsedTime = 0;
        currentFrame = 0;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void start(){
        isStopped = false;
    }

    public void update(double delta){
        if(!isStopped){
            elapsedTime += delta;
            if(elapsedTime >= animationDuration / frameCount){
                elapsedTime = 0.0;
                currentFrame++;
                if(currentFrame >= frameCount){
                    currentFrame = 0;
                }
            }
        }
    }

}
