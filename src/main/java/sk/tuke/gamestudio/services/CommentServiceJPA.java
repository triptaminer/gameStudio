package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
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
    public List<Comment> getComments(String gameName){
        final String STATEMENT_COMMENTS = "SELECT sc FROM Comment sc WHERE sc.game=:myGame ORDER BY sc.commentedAt DESC";

        return entityManager.createQuery(STATEMENT_COMMENTS)
                .setParameter("myGame",gameName)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void reset(){
        final String STATEMENT_RESET = "DELETE FROM comments";
        entityManager.createNativeQuery(STATEMENT_RESET).executeUpdate();
    }

    @Override
    public void addComment(GameStudioServices gss, String text) {
            addComment(new Comment(gss.getGameName(), gss.currentPlayer, text, new Date()));

    }
}
