package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    private static final String JDBC_PROTOCOL = "jdbc:postgresql://";

    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "rootPWD";
    private static final String JDBC_HOST = "localhost";
    private static final String JDBC_PORT = "5433";
    private static final String JDBC_DATABASE = "gamestudio";

    private static final String JDBC_URL = JDBC_PROTOCOL+JDBC_HOST+":"+JDBC_PORT+"/"+JDBC_DATABASE;






    @Override
    public void addScore(Score score) {

        final String STATEMENT="insert into score (game,  username, points, played_at) values (?, ?, ?, ?)";

        try{
            var connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
            var statement= connection.prepareStatement(STATEMENT);

            statement.setString(1, score.getGame());
            statement.setString(2, score.getUser());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedAt().getTime()));
            statement.execute();

        }
         catch (SQLException e) {
            throw new ServiceException();
        }

    }

    @Override
    public List<Score> getBestScores(String gameName) {
        return null;
    }

    @Override
    public void reset() {

    }
}
