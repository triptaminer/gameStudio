package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Occupation;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class OccupationServiceJPA implements OccupationService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addOccupation(Occupation occupation){
        entityManager.persist(occupation);
    }

    @Override
    public List<Occupation> getAllOccupations(){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Occupation sc";
        return entityManager.createQuery(STATEMENT_COMMENTS)
                .getResultList();
    }

    @Override
    public Occupation getOccupation(String name){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Occupation sc where sc.occupation=:myOccupation";
        return (Occupation) entityManager.createQuery(STATEMENT_COMMENTS)
                .setParameter("myOccupation",name)
                .getSingleResult();
    }

    @Override
    public void reset(){

    }

    public Occupation getOccupationById(int id){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Occupation sc where sc.id=:myId";
        try{
            return (Occupation) entityManager.createQuery(STATEMENT_COMMENTS)
                    .setParameter("myId",id)
                    .getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }
}
