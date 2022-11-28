package sk.tuke.gamestudio;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.game.tiles.ui.TileConsoleUI;
import sk.tuke.gamestudio.game.tiles.core.TileGame;
import sk.tuke.gamestudio.game.lights.ui.LightsConsoleUI;
import sk.tuke.gamestudio.game.lights.core.LightsGame;
import sk.tuke.gamestudio.game.mines.ui.ConsoleUI;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.services.GameStudioServices;

import javax.persistence.NoResultException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

        GAME_STUDIO_SERVICES.init.status();
        Scanner scanner=new Scanner(System.in);

        boolean shouldRepeat=true;

        printMenuHeader();


        askPlayerName();




        System.out.println("Hi "+GAME_STUDIO_SERVICES.currentPlayer);

        //TODO convert to Player
//        GAME_STUDIO_SERVICES.setUserName(GAME_STUDIO_SERVICES.currentPlayer.getName());


        while (shouldRepeat) {
            try {
                printMenuOptions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    private void printMenuOptions() throws SQLException, FileNotFoundException {
        System.out.println("\nPlease choose option:");
        System.out.println("M          play Minesweeper ("+GAME_STUDIO_SERVICES.ratingService.getAvgRating("Mines")+"*)");
        System.out.println("T          play Tiles ("+GAME_STUDIO_SERVICES.ratingService.getAvgRating("Tiles")+"*)");
        System.out.println("L          play Lights ("+GAME_STUDIO_SERVICES.ratingService.getAvgRating("Light")+"*)");
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

                    try {
                        Player p;
                        try {
                            p = GAME_STUDIO_SERVICES.playerService.getPlayerByUsername(name);
                        }
                        catch (NoResultException e){
                            p = null;
                        }
                        if(p==null){
                            //register
                            registerUser(scanner, name);
                        }
                        else{
                            //login
                            GAME_STUDIO_SERVICES.currentPlayer=p;
                            System.out.println("Logging in...");
                        }

                    } catch (FileNotFoundException | SQLException e) {
                        throw new ServiceException(e);
                    }
                }

        }while (isWrongName);

    }

    private void registerUser(Scanner scanner, String name) throws FileNotFoundException, SQLException {
        String aboutUser;
        String countryUser;
        String occupUser;
        List<Occupation> occupList = GAME_STUDIO_SERVICES.occupationService.getAllOccupations();
        int occupUserInt=0;

        boolean registration=true;
        do{

            String  aCheck="[\\w\\W\\d]+";
            String  cCheck="[\\w\\W]+";
            String  oCheck="[\\d]+";
            boolean occup=true;

            System.out.println("This name is not registered yet, please provide few detais about you \n" +
                    "(dont worry, we want just your money, your kidney is useless for us!)");
            System.out.println("Describe youself in few words (required):");
            aboutUser = scanner.nextLine();
            System.out.println("From which country are you? (required)");
            countryUser = scanner.nextLine();
            do{
                System.out.println("Choose your occupation:");
                for (int i = 0; i < occupList.size(); i++) {
                    System.out.println((i+1)+". "+occupList.get(i).getOccupationName());
                }
                occupUser = scanner.nextLine();
                if(!occupUser.matches(oCheck)){
                    System.out.println("Wrong input!");
                }
                else {
                    occupUserInt=Integer.parseInt(occupUser);
                    occup=false;
                }


            }while(occup);


            if (aboutUser.matches(aCheck) && countryUser.matches(cCheck) && occupList.size()>occupUserInt-1){
                registration=false;
            }
            else System.out.println("One ore more value(s) doesn't match required format! Please try again!");

        }while (registration);
        Country c=new Country(countryUser);
        GAME_STUDIO_SERVICES.countryService.addCountry(c);
        Occupation o=occupList.get(occupUserInt-1);
        GAME_STUDIO_SERVICES.currentPlayer=new Player(name,aboutUser,0,c,o);
        GAME_STUDIO_SERVICES.playerService.addPlayer(GAME_STUDIO_SERVICES.currentPlayer);
    }
}
