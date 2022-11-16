package sk.tuke.gamestudio.game.lights.ui;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.game.lights.core.LightsFieldState;
import sk.tuke.gamestudio.game.lights.core.LightsGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LightsConsoleUI {
    private final LightsGame game;
    private final LightsControls controls;

    private final Scanner scanner = new Scanner(System.in);

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
        System.out.println("\n\nCongrats "+game.GAME_STUDIO_SERVICES.getUserName()+", you got "+game.computeScore()+"pts in "+game.GAME_STUDIO_SERVICES.getGameName()+"\n\n");
        viewLevel(0);

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
            return false;
        }
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (matcher.matches()) {
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            if (row + 1 > game.getRowCount() || column + 1 > game.getColumnCount()) {
                System.err.println("Bad input");
            } else {
                game.switchTile(row, column);
            }

        } else
            System.err.println("Bad input");

        return true;
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
        List<Score> hiScores = null;
        try {
            hiScores = game.GAME_STUDIO_SERVICES.scoreService.getBestScores(game.GAME_STUDIO_SERVICES.getGameName());
        } catch (FileNotFoundException e) {
            throw new ServiceException("Missing configuration file! " + e);
        } catch (SQLException e) {
            throw new ServiceException("Cannot execute SQL query! " + e);
        }

        for (Score score : hiScores) {
            System.out.printf("%15s%15s%30s%n", score.getUserName(), score.getPoints(), score.getPlayedAt());
        }
    }
}
