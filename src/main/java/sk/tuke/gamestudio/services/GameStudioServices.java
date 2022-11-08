package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

import static sk.tuke.gamestudio.GameStudioConsole.scoreService;

public class GameStudioServices {

    private String userName;
    private String gameName;


    public GameStudioServices() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public static void processScore(String gameName, String userName, int score) {
        try {
            scoreService.addScore(
                    new Score(gameName, userName, score, new Date())
            );
        } catch (FileNotFoundException e) {
            throw new ServiceException("Missing configuration file! "+e);
        } catch (SQLException e) {
            throw new ServiceException("Cannot execute SQL query! "+e);
        }
    }
}
