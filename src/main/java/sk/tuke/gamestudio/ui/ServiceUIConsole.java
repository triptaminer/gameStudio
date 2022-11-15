package sk.tuke.gamestudio.ui;

import sk.tuke.gamestudio.entity.Rank;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;


public class ServiceUIConsole implements ServiceUI{

    GameStudioServices gss;
    Scanner scanner=new Scanner(System.in);

    public ServiceUIConsole(GameStudioServices gss) {
        this.gss=gss;
    }

    @Override
    public void askForRanking() {
        do{
            System.out.println("Please rate this game (1-very bad - 5-perfect) (or leave empty to skip ranking):");
            String reply=scanner.nextLine();
            //empty string will exit this service
            if(reply.equals("")) return;

            try{
                int rating = Integer.parseInt(reply);
                if (rating >= 1 && rating <= 5) {
                    try {
                        gss.rankService.addRanking(new Rank(gss.getGameName(), gss.currentPlayer, rating, new Date()));
                    } catch (FileNotFoundException e) {
                        throw new ServiceException("Missing configuration file! " + e);
                    } catch (SQLException e) {
                        throw new ServiceException("Cannot execute SQL query! " + e);
                    }
                    return;
                } else
                    System.out.println("Incorrect ranking value, please use numbers 1-5.");
            }
            catch (NumberFormatException e){
                System.out.println("Incorrect ranking, please use numbers 1-5.");
            }
        }while(true);
    }

    @Override
    public void askForComment() {

    }

    @Override
    public float viewRanking() {
        try {
            return gss.rankService.getAvgRanking(gss.getGameName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void viewComments() {

    }

    @Override
    public void viewHiscores() {

    }
}
