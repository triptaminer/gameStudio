package sk.tuke.gamestudio.game.tiles;

import sk.tuke.gamestudio.game.tiles.ui.TileConsoleUI;
import sk.tuke.gamestudio.game.tiles.core.TileGame;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GameStudioServices GAME_STUDIO_SERVICES = new GameStudioServices();

        TileGame game = new TileGame(GAME_STUDIO_SERVICES);
        TileConsoleUI ui = new TileConsoleUI(game);
//        ui.playGame();
        ui.Menu();
    }
}
