package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    /**
     * Adds new comment to storage
     * @param comment score object to be added
     */
    void addComment(Comment comment) throws FileNotFoundException, SQLException;

    /**
     * Loads 5 comments from given game
     * @param gameName Name of the game
     * @return List&lt;Comment&gt;
     */
    List<Comment> getComments(String gameName) throws FileNotFoundException, SQLException;

    /**
     * deletes all comments from storage
     */
    void reset() throws FileNotFoundException, SQLException;

    void addComment(GameStudioServices gss, String text) throws SQLException, FileNotFoundException;
}
