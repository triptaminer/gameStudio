package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score){
        entityManager.persist(score);
    }

    @Override
    public List<Score> getBestScores(String gameName){
        final String STATEMENT_BEST_SCORES = "SELECT sc FROM Score sc WHERE sc.game=:myGame ORDER BY sc.points DESC";

        return entityManager.createQuery(STATEMENT_BEST_SCORES)
                .setParameter("myGame",gameName)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset(){
        final String STATEMENT_RESET = "DELETE FROM score";
        entityManager.createNativeQuery(STATEMENT_RESET);
    }
}
