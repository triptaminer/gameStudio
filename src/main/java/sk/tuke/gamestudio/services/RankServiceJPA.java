package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rank;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Transactional
public class RankServiceJPA implements RankService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRanking(Rank rank) throws FileNotFoundException, SQLException {

    }

    @Override
    public Rank getAvgRanking(String gameName) throws FileNotFoundException, SQLException {
        //final String STATEMENT_COMMENTS = "SELECT avg(ranking) FROM rank WHERE game= ?";
        final String STATEMENT_RANKING = "SELECT avg(sc.ranking) FROM Rank sc WHERE sc.game=:myGame";

        return (Rank) entityManager.createQuery(STATEMENT_RANKING)
                .setParameter("myGame",gameName)
                .getSingleResult();
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM rank";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
