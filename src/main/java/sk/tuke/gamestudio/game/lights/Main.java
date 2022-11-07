package sk.tuke.gamestudio.game.lights;

import sk.tuke.gamestudio.game.lights.consoleui.LightsConsoleUI;
import sk.tuke.gamestudio.game.lights.core.LightsGame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        LightsGame game = new LightsGame();
        LightsConsoleUI LightsUI = new LightsConsoleUI(game);
//        ui.playGame();
        LightsUI.Menu();
    }
}
