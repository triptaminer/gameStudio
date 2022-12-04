package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Hash;

import javax.persistence.EntityManager;
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
        entityManager.persist(hash);
    }

    @Override
    public String[] getHashes(String hash) {
        final String STATEMENT_COMMENTS = "SELECT h FROM Hash h WHERE h.h1=:myH";
        System.out.println(hash);
        List<Hash> hs = entityManager.createQuery(STATEMENT_COMMENTS)
                .setParameter("myH", hash)
                .setMaxResults(5)
                .getResultList();
        return new String[]{hs.get(0).getH2(),hs.get(0).getH3()};
    }
}
