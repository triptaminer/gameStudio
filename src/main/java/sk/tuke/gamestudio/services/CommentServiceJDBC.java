package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.connectors.PostgresDirectConnector;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    @Override
    public void addComment(Comment comment) throws FileNotFoundException, SQLException {
        if(comment.getText().length()>0) {
            final String STATEMENT_ADD_SCORE = "INSERT INTO comments (game,username,text,commented_at) VALUES (?, ?, ?, ?)";
            PostgresDirectConnector connection = new PostgresDirectConnector();
            connection.setQuery(STATEMENT_ADD_SCORE, new Object[][]{{comment.getGameName(), comment.getUserName(), comment.getText(), new Timestamp(comment.getCommentedAt().getTime())}});
        }
    }

    @Override
    public List<Comment> getComments(String gameName) throws FileNotFoundException, SQLException {
        final String STATEMENT_COMMENTS = "SELECT username, text, commented_at FROM comments WHERE game= ? ORDER BY commented_at DESC";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        ResultSet rs = connection.getQuery(STATEMENT_COMMENTS, new Object[]{gameName});
        ArrayList comments = new ArrayList<Comment>();
        while (rs.next()) {
            comments.add(new Comment(gameName, rs.getString(1), rs.getString(2), rs.getTimestamp(3)));
        }
        return comments;
    }

    @Override
    public void reset() throws FileNotFoundException, SQLException {
        final String STATEMENT_RESET = "DELETE FROM comments";
        PostgresDirectConnector connection = new PostgresDirectConnector();
        connection.setQuery(STATEMENT_RESET);
    }
}
