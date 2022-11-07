package sk.tuke.gamestudio.game.tiles;

import sk.tuke.gamestudio.game.tiles.ui.TileConsoleUI;
import sk.tuke.gamestudio.game.tiles.core.TileGame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        TileGame game = new TileGame();
        TileConsoleUI ui = new TileConsoleUI(game);
//        ui.playGame();
        ui.Menu();
    }
}
