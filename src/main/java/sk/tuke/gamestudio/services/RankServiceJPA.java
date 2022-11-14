package sk.tuke.gamestudio.services;
import sk.tuke.gamestudio.entity.Rank;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Transactional
public class RankServiceJPA implements RankService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRanking(Rank rank){

    }

    @Override
    public float getAvgRanking(String gameName){
        final String STATEMENT_RANKING = "SELECT avg(sc.ranking) FROM Rank sc WHERE sc.game=:myGame";
        //TODO check if empty table still throws error

        float avg=0.0f;
        var val= entityManager.createQuery(STATEMENT_RANKING)
                        .setParameter("myGame",gameName)
                        .getSingleResult();
        if(val!=null){
            avg=Float.parseFloat(val.toString());
        }
        return avg ;
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM rank";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
