package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    /**
     * Adds new score to storage
     * @param score score object to be added
     */
    void addComment(Comment comment) throws FileNotFoundException, SQLException;

    /**
     * Loads 5 best scores from given game
     * @param gameName Name of the game
     * @return List&lt;Score&gt;
     */
    List<Score> getComments(String gameName) throws FileNotFoundException, SQLException;

    /**
     * deletes all scores from storage
     */
    void reset() throws FileNotFoundException, SQLException;

}
