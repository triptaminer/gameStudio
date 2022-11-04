package sk.tuke.gamestudio.game.kamene;

import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.kamene.core.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Game game = new Game();
        ConsoleUI ui = new ConsoleUI(game);
//        ui.playGame();
        ui.Menu();
    }
}
