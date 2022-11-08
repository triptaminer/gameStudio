package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rank;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RankServiceJDBC implements RankService {

    @Override
    public void addRanking(Rank rank) throws FileNotFoundException, SQLException {
        if (rank.getRanking() > 0) {

            /* uz som sa bal, ze nieco take v postgres nie je :D
INSERT INTO customers (name, email)
VALUES('Microsoft','hotline@microsoft.com')
ON CONFLICT (name)
DO
UPDATE SET email = EXCLUDED.email || ';' || customers.email;
             */
            //final String STATEMENT_ADD_SCORE = "INSERT INTO rankings (game,username,rank,ranked_at) VALUES (?, ?, ?, ?)";
            final String STATEMENT_ADD_SCORE = "INSERT INTO rankings (game,username,rank,ranked_at) VALUES (?, ?, ?, ?) ON CONFLICT (username,rank) DO UPDATE SET game = ?,username = ?,rank = ?,ranked_at = ?";
            PostgresDirectConnector connection = new PostgresDirectConnector();
            connection.setQuery(STATEMENT_ADD_SCORE, new Object[][]{{rank.getGame(), rank.getUsername(), rank.getRanking(), new Timestamp(rank.getRankedAt().getTime())}});
        }
    }

    @Override
    public List<Rank> getAvgRanking(String game) throws FileNotFoundException, SQLException {
        final String STATEMENT_AVG_RANK = "SELECT avg(rank) FROM score WHERE game= ? ORDER BY points DESC LIMIT 5";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        ResultSet rs = connection.getQuery(STATEMENT_AVG_RANK, new Object[]{game});
        ArrayList scores = new ArrayList<Rank>();
        while (rs.next()) {
            scores.add(new Rank(game, rs.getString(1), rs.getInt(2), rs.getTimestamp(3)));
        }
        return scores;
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM ranking";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        connection.setQuery(STATEMENT_RESET);
    }
}
