package sk.tuke.gamestudio.services;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRating(Rating rating){
        final String STATEMENT_ADD_SCORE = "INSERT INTO rating (game,username,value,rated_at) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT ON CONSTRAINT uniq DO UPDATE SET value = EXCLUDED.value, rated_at = EXCLUDED.rated_at";
        Rating existing= getRating(rating.getUser(), rating.getGame());
        if(existing!=null){
            existing.setValue(rating.getValue());
            existing.setRatedAt(rating.getRatedAt());
        }
        else
        {
            entityManager.persist(rating);
        }
    }

    public void addRating(GameStudioServices gss, int rating){
     if(gss.getGameName()!="" && gss.getUserName()!="" && rating>0)
        addRating(new Rating(gss.getGameName(), gss.currentPlayer, rating, new Date()));
    }

    @Override
    public float getAvgRating(String gameName){
        final String STATEMENT_RANKING = "SELECT avg(sc.value) FROM Rating sc WHERE sc.game=:myGame";
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
    public Rating getRating(Player player, String game){
        final String STATEMENT_RANKING = "SELECT sc FROM Rating sc WHERE sc.game=:myGame and sc.user=:myUser";
        Rating r;
        try{
            r = (Rating) entityManager.createQuery(STATEMENT_RANKING)
                    .setParameter("myGame", game)
                    .setParameter("myUser", player)
                    .getSingleResult();
        }
        catch (NoResultException e){
            r=null;
        }
    return r;
    }

    @Override
    public void reset(){
        final String STATEMENT_RESET = "DELETE FROM rating";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
