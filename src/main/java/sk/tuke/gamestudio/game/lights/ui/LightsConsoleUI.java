package sk.tuke.gamestudio.game.lights.ui;

import sk.tuke.gamestudio.game.lights.core.LightsFieldState;
import sk.tuke.gamestudio.game.lights.core.LightsGame;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LightsConsoleUI {
    private LightsGame game;
    private final LightsControls controls;

    private Scanner scanner = new Scanner(System.in);

    //private static final Pattern INPUT_PATTERN = Pattern.compile("[a-zA-Z0-9]*");

    private static final Pattern INPUT_PATTERN = Pattern.compile("([A-I])([1-99])");

    enum Option {
        //        CONTINUE, NEW_GAME, HISCORES, EXIT
        NEW_GAME, HISCORES, EXIT
    }

    enum OptionGame {
        EASY_3X3, MEDIUM_4X4, HARD_5X5, CUSTOM_SIZE, BACK
    }

    public LightsConsoleUI(LightsGame game) {
        this.game = game;
        controls = new LightsControls();

    }


    public boolean playGame() throws IOException {
        do {
            printGame();
            if (!processInput()) {
                //we are leaving aa game
                return false;
            }
        } while (game.getState() == LightsFieldState.PLAYING);
        printGame();
        saveNickname();

        return true;
    }

    private void printGame() {
        System.out.println(game.getState() + " " + game.getTimer());
        System.out.print(" ");
        for (int column = 0; column < game.getColumnCount(); column++) {
            System.out.print("  ");
            System.out.print(column + 1);
        }

        System.out.println();
        for (int row = 0; row < game.getRowCount(); row++) {
            System.out.print((char) (row + 'A'));
            for (int column = 0; column < game.getColumnCount(); column++) {
                boolean value = game.getTile(row, column);

                System.out.printf("%3s", value ? "O" : ".");

            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean processInput() throws IOException {
        System.out.println("Enter command (X - exit, OA1 - open, MB3 - mark: ");
        String line = scanner.nextLine().toUpperCase().trim();
        if ("X".equals(line)) {
            //System.exit(0);
            game.quit();
            return false;
        }
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (matcher.matches()) {
//            System.out.println("dbg: "+matcher.group(0)+" "+matcher.group(1)+" "+matcher.group(2));
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;

//            System.out.println("dbg xy: "+row+"/"+column);
            if (row + 1 > game.getRowCount() || column + 1 > game.getColumnCount()) {
                System.err.println("Bad input");
            } else {
                game.switchTile(row, column);
            }

        } else
            System.err.println("Bad input");

        return true;
    }

    private void saveNickname() {
        System.out.println("Congratulations! You have solved puzzle in " + game.getTimer() + "\n"
                + "Please enter your nickname:");
        String name = scanner.nextLine().toLowerCase().trim();
        game.scores.saveScore(game.getCategory(), name, game.getActualTime());
        try {
            game.scores.saveScores();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Menu() throws IOException {
        while (true) {
            switch (showMenu()) {
                case NEW_GAME:
                    //TODO: remove category
                    game.setGameProperties(5, 5, 0);
                    playGame();
                    break;
                case HISCORES:
                    viewLevel(0);
                    break;
                case EXIT:
                    game.scores.saveScores();
                    return;
            }
        }

    }

    public Option showMenu() {
        System.out.println("Menu.");
        for (var option : Option.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option.toString().replace("_", " "));
        }
        System.out.println("-----------------------------------------------");

        var selection = -1;
        do {
            System.out.println("Option: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of menu items 1-" + Option.values().length + ".");
            }
        } while (selection <= 0 || selection > Option.values().length);

        return Option.values()[selection - 1];


    }

    private void viewLevel(int i) {
        System.out.println("HiScores:");
        game.scores.getHiScores(i).forEach((integer, s) -> {
            System.out.printf("%15s%15s%n", s, game.niceTimer(integer * 1000));
        });

        //TODO apply findPersonByName() from register?
        System.out.println("\n");
        do {
            System.out.println("Enter x for exit:");
        } while (!scanner.nextLine().equalsIgnoreCase("x"));
        System.out.println("\n\n\n\n\n");
    }


}
