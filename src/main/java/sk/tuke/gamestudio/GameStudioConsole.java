package sk.tuke.gamestudio;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.tiles.ui.TileConsoleUI;
import sk.tuke.gamestudio.game.tiles.core.TileGame;
import sk.tuke.gamestudio.game.lights.ui.LightsConsoleUI;
import sk.tuke.gamestudio.game.lights.core.LightsGame;
import sk.tuke.gamestudio.game.mines.ui.ConsoleUI;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.ScoreService;
import sk.tuke.gamestudio.services.ScoreServiceJDBC;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class GameStudioConsole {

    private String playerName;

    @Autowired
    public GameStudioServices GAME_STUDIO_SERVICES;

    //public ScoreService scoreService;

    public static void main(String[] args) throws IOException, SQLException {
        new GameStudioConsole().run();
    }
    public void run() throws IOException {

        //forSpring GAME_STUDIO_SERVICES = new GameStudioServices();


        Scanner scanner=new Scanner(System.in);

        boolean shouldRepeat=true;

        printMenuHeader();
        askPlayerName();
        System.out.println("Hi "+playerName);

        GAME_STUDIO_SERVICES.setUserName(playerName);

        while (shouldRepeat) {
            printMenuOptions();
            String line = scanner.nextLine();
            switch (line.toUpperCase()) {
                case "M":
                    System.out.println("Starting Minesweeper...");
                    GAME_STUDIO_SERVICES.setGameName("Mines");
//                    Field field = new Field(8, 8, 10);
                    Field mines = new Field(8, 8, 1,GAME_STUDIO_SERVICES);
                    ConsoleUI uiMine = new ConsoleUI(mines);
                    uiMine.menu();
                    break;
                case "T":
                    System.out.println("Starting Tiles...");
                    GAME_STUDIO_SERVICES.setGameName("Tiles");
                    TileGame tiles = new TileGame(GAME_STUDIO_SERVICES);
                    TileConsoleUI uiTile = new TileConsoleUI(tiles);
                    uiTile.Menu();
                    break;
                case "L":
                    System.out.println("Starting Lights...");
                    GAME_STUDIO_SERVICES.setGameName("Lights");
                    LightsGame light = new LightsGame(GAME_STUDIO_SERVICES);
                    LightsConsoleUI lightsUI = new LightsConsoleUI(light);
                    lightsUI.Menu();
                    break;
                case "X":
                    System.out.println("Exiting...");
                    shouldRepeat=false;
                    break;
                default:
                    System.out.println("Wrong input! Please try again:");
            }
        }
    }

    private void printMenuHeader() {
        System.out.println("-----------------------");
        System.out.println("Welcome to Game Studio.");
        System.out.println("-----------------------");

    }

    private void printMenuOptions() {
        System.out.println("\nPlease choose option:");
        System.out.println("M          play Minesweeper");
        System.out.println("T          play Tiles");
        System.out.println("L          play Lights");
        System.out.println("");
        System.out.println("X          exit\n\n");
        System.out.println("Your choise?");
    }

    private void askPlayerName(){
        System.out.println("What is your name? (3-15 letters)");
        Scanner scanner=new Scanner(System.in);

        boolean isWrongName=false;
        do{
            String name= scanner.nextLine().trim();
            if(name.length()<3){
                System.out.println("Your name is too short.");
            } else
                if(name.length()>15){
                System.out.println("Your name is too long.");
                }
                else {
                    playerName=name;
                }

        }while (isWrongName);

    }
}
