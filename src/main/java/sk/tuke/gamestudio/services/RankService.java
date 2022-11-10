package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rank;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface RankService {
    /**
     * Adds new score to storage
     * @param rank score object to be added
     */
    void addRanking(Rank rank) throws FileNotFoundException, SQLException;

    /**
     * Loads 5 best scores from given game
     *
     * @param gameName Name of the game
     * @return List&lt;Score&gt;
     */
    Rank getAvgRanking(String gameName) throws FileNotFoundException, SQLException;

    /**
     * deletes all scores from storage
     */
    void reset() throws FileNotFoundException, SQLException;

}
