package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment){
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String gameName) throws FileNotFoundException, SQLException {
        final String STATEMENT_COMMENTS = "SELECT sc FROM Comment sc WHERE sc.game=:myGame ORDER BY sc.commented_at DESC";

        return entityManager.createQuery(STATEMENT_COMMENTS)
                .setParameter("myGame",gameName)
                .getResultList();
    }

    @Override
    public void reset(){
        final String STATEMENT_RESET = "DELETE FROM comments";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }
}
