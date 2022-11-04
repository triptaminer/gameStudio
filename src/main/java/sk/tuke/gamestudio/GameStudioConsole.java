package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.mines.ConsoleUI;
import sk.tuke.gamestudio.game.mines.core.Field;

import java.io.IOException;
import java.util.Scanner;

public class GameStudioConsole {

    private static String playerName;

    public static void main(String[] args) throws IOException {

        Scanner scanner=new Scanner(System.in);


        boolean shouldRepeat=true;

        printMenuHeader();

        askPlayerName();
        System.out.println("Hi "+playerName);

        while (shouldRepeat) {
            printMenuOptions();
            String line = scanner.nextLine();
            switch (line.toUpperCase()) {
                case "M":
                    System.out.println("Starting Minesweeper...");
                    Field field = new Field(8, 8, 10);
                    ConsoleUI ui = new ConsoleUI(field);
                    ui.play();

                    processScore("Mines",playerName,field.computeScore());
                    break;
                case "T":
                    System.out.println("Starting Tiles...");
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

    private static void printMenuHeader() {
        System.out.println("-----------------------");
        System.out.println("Welcome to Game Studio.");
        System.out.println("-----------------------");

    }

    private static void printMenuOptions() {
        System.out.println("\nPlease choose option:");
        System.out.println("M          play Minesweeper");
        System.out.println("T          play Tiles");
        System.out.println("");
        System.out.println("X          exit\n\n");
        System.out.println("Your choise?");
    }

    private static void askPlayerName(){
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
    private static void processScore(String gameName, String userName, int score){



    }
}
