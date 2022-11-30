package sk.tuke.gamestudio.game.mines;

import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.ui.ConsoleUI;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameStudioServices GAME_STUDIO_SERVICES = new GameStudioServices();

        Field field = new Field(8, 8, 10);
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
}
