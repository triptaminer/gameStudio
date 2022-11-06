package sk.tuke.gamestudio.game.kamene;

import sk.tuke.gamestudio.game.kamene.consoleui.TileConsoleUI;
import sk.tuke.gamestudio.game.kamene.core.TileGame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        TileGame game = new TileGame();
        TileConsoleUI ui = new TileConsoleUI(game);
//        ui.playGame();
        ui.Menu();
    }
}
