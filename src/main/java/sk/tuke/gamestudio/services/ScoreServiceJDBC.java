package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService {

    @Override
    public void addScore(Score score) throws FileNotFoundException, SQLException {

        final String STATEMENT_ADD_SCORE = "INSERT INTO score (game,username,points,played_at) VALUES (?, ?, ?, ?)";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        connection.setQuery(STATEMENT_ADD_SCORE, new Object[][]{{score.getGame(), score.getUsername(), score.getPoints(), new Timestamp(score.getPlayedAt().getTime())}});
    }

    @Override
    public List<Score> getBestScores(String game) throws FileNotFoundException, SQLException {
        final String STATEMENT_BEST_SCORES = "SELECT username, points, played_at FROM score WHERE game= ? ORDER BY points DESC LIMIT 5";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        ResultSet rs = connection.getQuery(STATEMENT_BEST_SCORES, new Object[]{game});
        ArrayList scores = new ArrayList<Score>();
        while (rs.next()) {
            scores.add(new Score(game, rs.getString(1), rs.getInt(2), rs.getTimestamp(3)));
        }
        return scores;
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM score";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        connection.setQuery(STATEMENT_RESET);
    }
}
