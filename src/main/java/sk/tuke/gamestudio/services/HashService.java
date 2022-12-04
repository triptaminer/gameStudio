package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Hash;

public interface HashService {

    void addHash(Hash hash);

    String[] getHashes(String hash);

}
