package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class PlayerServiceJPA implements PlayerService{

    @PersistenceContext
    private EntityManager entityManager;


    public void addPlayer(Player player){
        entityManager.persist(player);
    }


    @Override
    public List<Player> getPlayers(){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Player sc";

        return entityManager.createQuery(STATEMENT_COMMENTS)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public Player getPlayerByUsername(String name){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Player sc WHERE sc.name=:myName";
try {
    return (Player) entityManager.createQuery(STATEMENT_COMMENTS)
            .setParameter("myName", name)
            .getSingleResult();
}
catch (NoResultException e){
    System.out.println("DB is empty or cannot find user with name "+name+": "+e);
    return null;
}
}

    @Override
    public void reset(){
        final String STATEMENT_RESET = "DELETE FROM player";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
