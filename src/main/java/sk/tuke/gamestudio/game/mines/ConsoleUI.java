package sk.tuke.gamestudio.game.mines;

import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.game.mines.core.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final Field field;

    private Scanner scanner = new Scanner(System.in);

    private static final Pattern INPUT_PATTERN = Pattern.compile("([OM])([A-I])([1-99])");

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        do {
            print();
            processInput();
        } while (field.getState() == FieldState.PLAYING);
        print();
    }

    private void print() {
        System.out.println(field.getState());
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" ");
            System.out.print(column + 1);
        }
        System.out.println();
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) (row + 'A'));
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                System.out.print(" ");
                switch (tile.getState()) {
                    case OPEN:
                        if (tile instanceof Clue)
                            System.out.print(String.valueOf(((Clue) tile).getValue()).replace("0"," "));
                        else
                            System.out.print("X");
                        break;
                    case CLOSED:
                        System.out.print("-");
                        break;
                    case MARKED:
                        System.out.print("1");
                        break;
                }
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println("Enter command (X - exit, OA1 - open, MB3 - mark: ");
        String line = scanner.nextLine().toUpperCase().trim();
        if ("X".equals(line))
            System.exit(0);
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (matcher.matches()) {
            int row = matcher.group(2).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(3)) - 1;

            if (row+1 > field.getRowCount() || column+1 > field.getColumnCount()) {
                System.err.println("Bad input");
            } else {
                if ("O".equals(matcher.group(1)))
                    field.openTile(row, column);
                else
                    field.markTile(row, column);
            }

        } else
            System.err.println("Bad input");
    }
}
