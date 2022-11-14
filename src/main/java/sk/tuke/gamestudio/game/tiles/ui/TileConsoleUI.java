package sk.tuke.gamestudio.game.tiles.ui;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.game.tiles.core.TileFieldState;
import sk.tuke.gamestudio.game.tiles.core.TileGame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileConsoleUI {
    private TileGame game;
    private final TileControls controls;

    private Scanner scanner = new Scanner(System.in);

    private static final Pattern INPUT_PATTERN = Pattern.compile("[a-zA-Z0-9]*");

    enum Option {
        //        CONTINUE, NEW_GAME, HISCORES, EXIT
        NEW_GAME, HISCORES, EXIT
    }

    enum OptionGame {
        EASY_3X3, MEDIUM_4X4, HARD_5X5, CUSTOM_SIZE, BACK
    }

    public TileConsoleUI(TileGame game) {
        this.game = game;
        controls = new TileControls();

    }

    public boolean playGame() throws IOException {
        do {
            printGame();
            if (!processInput()) {
                return false;
            }
        } while (game.getState() == TileFieldState.PLAYING);
        printGame();
        System.out.println("\n\nCongrats "+game.GAME_STUDIO_SERVICES.getUserName()+", you got "+game.computeScore()+"pts in "+game.GAME_STUDIO_SERVICES.getGameName()+"\n\n");
        viewLevel(game.getCategory());
        return true;
    }

    private void printGame() {
        System.out.println(game.getState() + " " + game.getTimer());

        System.out.println();
        for (int row = 0; row < game.getRowCount(); row++) {
            for (int column = 0; column < game.getColumnCount(); column++) {
                int value = game.getTile(row, column);
                System.out.printf("%3s", value > 0 ? value : " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean processInput() throws IOException {
        System.out.println("Enter command (wasd or 'up','left','down','right' or x for exit)");
        String line = scanner.nextLine().toLowerCase().trim();
        if ("x".equals(line)) {
        //TODO find a better way how to destroy/leave current game?
            return false;
        }
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (matcher.matches()) {

            int direction = controls.translate(line);
            if (direction == -1) {
                System.err.println("Bad input");
                return false;
            }

            String emptyTile = game.getEmpty().toString().replace("[", "").replace("]", "");

            int row = Integer.parseInt(emptyTile.split("x")[0]);
            int column = Integer.parseInt(emptyTile.split("x")[1]);

            game.moveTile(row, column, direction);

        } else
            System.err.println("Bad input");
        return true;
    }

    public void Menu() throws IOException {
        while (true) {
            switch (showMenu()) {
                //TODO: save/load game state
//            case CONTINUE:
//                Game game = new Game(gameState);
//                playGame();
//                break;
                case NEW_GAME:
                    chooseLevel();
                    break;
                case HISCORES:
                    chooseHiscores();
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

    public void chooseLevel() throws IOException {
        while (true) {
            switch (showLevel()) {
                case EASY_3X3:
                    game.setGameProperties(3, 3, 0);
                    playGame();
                    break;
                case MEDIUM_4X4:
                    game.setGameProperties(4, 4, 1);
                    playGame();
                    break;
                case HARD_5X5:
                    game.setGameProperties(5, 5, 2);
                    playGame();
                    break;
                case CUSTOM_SIZE:
                    setCustomSize();
                    break;
                case BACK:
                    showMenu();
                    return;
            }
        }

    }

    public void setCustomSize() throws IOException {
        System.out.println("Enter number of rows:");
        int rows = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Enter number of columns:");
        int cols = Integer.parseInt(scanner.nextLine().trim());
        game.setGameProperties(rows, cols, 3);
        playGame();
    }

    public OptionGame showLevel() {
        System.out.println("Choose level.");
        for (var option : OptionGame.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option.toString().replace("_", " "));
        }
        System.out.println("-----------------------------------------------");

        var selection = -1;
        do {
            System.out.println("Option: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of menu items 1-" + OptionGame.values().length + ".");
            }
        } while (selection <= 0 || selection > OptionGame.values().length);

        return OptionGame.values()[selection - 1];


    }

    public void chooseHiscores() throws IOException {
        while (true) {
            switch (showHiscores()) {
                case EASY_3X3:
                    viewLevel(0);
                    break;
                case MEDIUM_4X4:
                    viewLevel(1);
                    break;
                case HARD_5X5:
                    viewLevel(2);
                    break;
                case CUSTOM_SIZE:
                    viewLevel(3);
                    break;
                case BACK:
                //TODO how to go back?
                    break;
            }
        }
    }

    public OptionGame showHiscores() {
        System.out.println("Hiscores.");
        for (var option : OptionGame.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option.toString().replace("_", " "));
        }
        System.out.println("-----------------------------------------------");

        var selection = -1;
        do {
            System.out.println("Option: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of menu items 1-" + OptionGame.values().length + ".");
            }
        } while (selection <= 0 || selection > OptionGame.values().length);

        return OptionGame.values()[selection - 1];

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
