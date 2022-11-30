package sk.tuke.gamestudio.game.lights;

import sk.tuke.gamestudio.game.lights.ui.LightsConsoleUI;
import sk.tuke.gamestudio.game.lights.core.LightsGame;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameStudioServices GAME_STUDIO_SERVICES = new GameStudioServices();

        LightsGame game = new LightsGame();

        LightsConsoleUI LightsUI = new LightsConsoleUI(game);
//        ui.playGame();
        LightsUI.Menu();
    }
}
