package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CountryServiceJPA implements CountryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCountry(Country country){
        entityManager.persist(country);
    }

    @Override
    public List<Country> getAllCountries(){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Country sc";
        return entityManager.createQuery(STATEMENT_COMMENTS)
                .getResultList();
    }

    @Override
    public Country getCountry(String name){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Country sc where sc.country=:myCountry";
        return (Country) entityManager.createQuery(STATEMENT_COMMENTS)
                .setParameter("myCountry",name)
                .getSingleResult();
    }

    @Override
    public void reset(){

    }
}
