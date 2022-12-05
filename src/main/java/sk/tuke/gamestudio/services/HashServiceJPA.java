package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Hash;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.util.List;

@Transactional
public class HashServiceJPA implements HashService{

    @PersistenceContext
    private EntityManager entityManager;

    //@Override
    public void addHash(Hash hash) {
        final String STATEMENT_ADD_SCORE = "INSERT INTO hash (h1,h2,h3) VALUES (?, ?, ?) " +
                "ON CONFLICT ON CONSTRAINT uniq DO UPDATE SET h2 = EXCLUDED.h2, h3 = EXCLUDED.h3";
        Hash existing=null;
        try{
            existing = getHash(hash.getH1());
        }
        catch (NoResultException e){
            System.out.println("no result: "+e);
        }
        if(existing!=null){
            existing.setH2(hash.getH2());
            existing.setH3(hash.getH3());
        }
        else
            entityManager.persist(hash);
    }

    public Hash getHash(String h1){
        final String STATEMENT_HASH = "SELECT h FROM Hash h WHERE h.h1=:myH";
        Hash hs = (Hash) entityManager.createQuery(STATEMENT_HASH)
                .setParameter("myH", h1)
                .setMaxResults(5)
                .getSingleResult();
        return hs;

    }
    @Override
    public String[] getHashes(String hash) {
        Hash hs=getHash(hash);
        return new String[]{hs.getH2(),hs.getH3()};
    }
}
