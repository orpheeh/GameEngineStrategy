package example;

import ges.base.GESGameEngine;
import java.awt.*;


public class Game extends GESGameEngine {

    protected Game() {
    }

    @Override
    protected void initialization() {
        setWindowWidth(800);
        setWindowHeight(600);
        setGameName("Le nom du jeu");
    }

    @Override
    protected void processEvent() {

    }

    @Override
    protected void updateGameLogic(double delta) {

    }

    @Override
    protected void renderGame(Graphics2D g2d) {

    }

    @Override
    protected void dispose() {

    }

    @Override
    protected void onWindowClosingCallback() {

    }

    public static void main(String[] argc){
        Game game = new Game();
        game.startGameEngine();
    }
}
