package sk.tuke.gamestudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.GameStudioConsole;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.ui.ServiceUI;
import sk.tuke.gamestudio.ui.ServiceUIConsole;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;


public class GameStudioServices {

    //public final CommentService commentService;
    private String userName;
    private String gameName;

    @Autowired
    public ScoreService scoreService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public RankService rankService;

    @Autowired
    public PlayerService playerService;
    @Autowired
    public CountryService countryService;
    @Autowired
    public OccupationService occupationService;

    public ServiceUI serviceUI;

    public Player currentPlayer;

    public GameStudioServices() {
//        scoreService = new ScoreServiceJDBC();
   //     scoreService = new ScoreServiceJPA();
   //     commentService = new CommentServiceJDBC();
   //     rankService = new RankServiceJDBC();
        serviceUI= new ServiceUIConsole(this);
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

    public void processScore(String gameName, String userName, int score) {
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
