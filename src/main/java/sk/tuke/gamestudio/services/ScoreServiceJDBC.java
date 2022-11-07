package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
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
    public void addScore(Score score) throws FileNotFoundException, SQLException {

        final String STATEMENT_ADD_SCORE = "INSERT INTO score (game,username,points,played_at) VALUES (?, ?, ?, ?)";

        PostgresDirectConnector connection=new PostgresDirectConnector();


        connection.setQuery(
                STATEMENT_ADD_SCORE,
                new Object[][]{
                        {score.getGame(),score.getUsername(),score.getPoints(),new Timestamp(score.getPlayedAt().getTime())}
                }
        );
//        try(
//                Connection connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
//                PreparedStatement statement = connection.prepareStatement(STATEMENT_ADD_SCORE)
//        )
//        {
//            statement.setString(1, score.getGame());
//            statement.setString(2, score.getUsername());
//            statement.setInt(3, score.getPoints());
//            statement.setTimestamp(4,new Timestamp(score.getPlayedAt().getTime()));
//            statement.executeUpdate();
//
//        }catch (SQLException e){
//            throw new ServiceException();
//        }
    }

    @Override
    public List<Score> getBestScores(String game) {
        final String STATEMENT_BEST_SCORES = "SELECT game, username, points, played_at FROM score WHERE game= ? ORDER BY points DESC LIMIT 5";

        try( Connection connection =DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(STATEMENT_BEST_SCORES)
        )
        {
            statement.setString(1,game);
            try(ResultSet rs = statement.executeQuery()){
                ArrayList scores= new ArrayList<Score>();
                while(rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        }catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void reset() {
        final String STATEMENT_RESET = "DELETE FROM score";
        try(Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement()
        )
        {
            statement.executeUpdate(STATEMENT_RESET);
        }catch(SQLException e){
            throw new ServiceException(e);
        }
    }}
