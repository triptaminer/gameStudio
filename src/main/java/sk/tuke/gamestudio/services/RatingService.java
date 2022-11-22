package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface RatingService {
    /**
     * Adds new score to storage
     * @param rating object to be added
     */
    void addRating(Rating rating) throws FileNotFoundException, SQLException;

    /**
     * Loads 5 best scores from given game
     *
     * @param gameName Name of the game
     * @return List&lt;Score&gt;
     */
    float getAvgRating(String gameName) throws FileNotFoundException, SQLException;

    /**
     * deletes all scores from storage
     */
    void reset() throws FileNotFoundException, SQLException;

    Rating getRating(Player player, String game);
}
