package sk.tuke.gamestudio;

import java.util.Scanner;

public class GameStudioConsole {

    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);


        boolean shouldRepeat=true;

        printMenu();

        while (shouldRepeat) {

            String line = scanner.nextLine();
            switch (line.toUpperCase()) {
                case "M":
                    System.out.println("Starting Minesweeper...");
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

    private static void printMenu() {
        System.out.println("-----------------------");
        System.out.println("Welcome to Game Studio.");
        System.out.println("-----------------------");
        System.out.println("\nPlease choose option:");
        System.out.println("M          play Minesweeper");
        System.out.println("T          play Tiles");
        System.out.println("");
        System.out.println("X          exit\n\n");
        System.out.println("Your choise?");
    }

}
