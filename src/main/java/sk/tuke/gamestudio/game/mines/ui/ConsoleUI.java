package sk.tuke.gamestudio.game.mines.ui;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sk.tuke.gamestudio.GameStudioConsole.*;

public class ConsoleUI {
    private final Field field;

    private Scanner scanner = new Scanner(System.in);

    private static final Pattern INPUT_PATTERN = Pattern.compile("([OM])([A-I])([1-99])");

    public ConsoleUI(Field field) {
        this.field = field;
    }

    enum Option {
        //        CONTINUE, NEW_GAME, HISCORES, EXIT
        NEW_GAME, HISCORES, EXIT
    }


    public void play() {
        do {
            print();
            processInput();
        } while (field.getState() == FieldState.PLAYING);
        print();

        System.out.println("\n\nCongrats "+field.GAME_STUDIO_SERVICES.getUserName()+", you got "+field.computeScore()+"pts in "+field.GAME_STUDIO_SERVICES.getGameName()+"\n\n");
        viewHiscores();
        field.GAME_STUDIO_SERVICES.serviceUI.askForRanking();

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
        if ("X".equals(line)) {
            //System.exit(0);
            field.quit();
            return;
        }
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

    public void menu() throws IOException {
        while (true) {
            switch (showMenu()) {
                //TODO: save/load game state
//             case CONTINUE:
//                Game game = new Game(gameState);
//                playGame();
//                 break;
                case NEW_GAME:
                    play();
                    break;
                case HISCORES:
                    viewHiscores();
                    break;
                case EXIT:
//                    game.scores.saveScores();
                    return;
            }
        }
    }

    private void viewHiscores() {
        System.out.println("HiScores:");
        List<Score> hiScores=null;
        try {
            hiScores= field.GAME_STUDIO_SERVICES.scoreService.getBestScores(field.gameName);
        } catch (FileNotFoundException e) {
            throw new ServiceException("Missing configuration file! "+e);
        } catch (SQLException e) {
            throw new ServiceException("Cannot execute SQL query! "+e);
        }

        for (Score score:hiScores){
            System.out.printf("%15s%15s%30s%n",score.getUsername(),score.getPoints(),score.getPlayedAt());
        }
    }

    public ConsoleUI.Option showMenu() {
        System.out.println("Menu.");
        for (var option : ConsoleUI.Option.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option.toString().replace("_", " "));
        }
        System.out.println("-----------------------------------------------");

        var selection = -1;
        do {
            System.out.println("Option: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please choose one of menu items 1-" + ConsoleUI.Option.values().length + ".");
            }
        } while (selection <= 0 || selection > ConsoleUI.Option.values().length);

        return ConsoleUI.Option.values()[selection - 1];


    }

}
