package sk.tuke.game.mines;

import sk.tuke.game.mines.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(8, 8, 10);
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
}
