package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RatingServiceJDBC implements RatingService {

    @Override
    public void addRating(Rating rating) throws FileNotFoundException, SQLException {
        if (rating.getValue() > 0) {

            /* uz som sa bal, ze nieco take v postgres nie je :D
INSERT INTO customers (name, email)
VALUES('Microsoft','hotline@microsoft.com')
ON CONFLICT (name)
DO
UPDATE SET email = EXCLUDED.email || ';' || customers.email;
             */
            //final String STATEMENT_ADD_SCORE = "INSERT INTO rankings (game,username,rank,ranked_at) VALUES (?, ?, ?, ?)";
//            final String STATEMENT_ADD_SCORE = "INSERT INTO rankings (game,username,rank,ranked_at) VALUES (?, ?, ?, ?) ON CONFLICT (username,rank) DO UPDATE SET game = ?,username = ?,rank = ?,ranked_at = ?";
            final String STATEMENT_ADD_SCORE = "INSERT INTO rankings (game,username,rank,ranked_at) VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT ON CONSTRAINT uniq DO UPDATE SET rank = EXCLUDED.rank, ranked_at = EXCLUDED.ranked_at";
            PostgresDirectConnector connection = new PostgresDirectConnector();
            connection.setQuery(STATEMENT_ADD_SCORE, new Object[][]{{rating.getGame(), rating.getUsername(), rating.getValue(), new Timestamp(rating.getRatedAt().getTime())}});
        }
    }

    @Override
    public float getAvgRating(String game) throws FileNotFoundException, SQLException {
        final String STATEMENT_AVG_RANK = "SELECT avg(rank) FROM score WHERE game= ?";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        ResultSet rs = connection.getQuery(STATEMENT_AVG_RANK, new Object[]{game});
//        ArrayList scores = new ArrayList<Rank>();
//        while (rs.next()) {
//            scores.add(new Rank(game, rs.getString(1), rs.getInt(2), rs.getTimestamp(3)));
//        }
//        return new Rank(game, rs.getString(1), rs.getInt(2), rs.getTimestamp(3));
        return 0.0f;
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM ranking";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        connection.setQuery(STATEMENT_RESET);
    }

    @Override
    public Rating getRating(Player player, String game) {
        return null;
    }
}
