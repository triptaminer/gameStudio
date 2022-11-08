package sk.tuke.gamestudio.services;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommentServiceTest {

    private final CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset() throws SQLException, FileNotFoundException {
        commentService.addComment(new Comment("Mines", "Feri", "Lorem Ipsum", new Date()));
        assertTrue(commentService.getComments("Mines").size()>0);
        commentService.addComment(new Comment("Tiles", "Feri", "Lorem Ipsum", new Date()));
        assertTrue(commentService.getComments("Tiles").size()>0);

        commentService.reset();
        assertEquals(0,commentService.getComments("Mines").size());

    }

    @Test
    public void testAddComment() throws SQLException, FileNotFoundException {
        commentService.reset();
        var date = new Date();
        final String gameName="Mines";

        commentService.addComment(new Comment(gameName,"Jeno","Lorem Ipsum",date));

        var comments = commentService.getComments(gameName);

        assertEquals(1, comments.size());

        assertEquals(gameName,comments.get(0).getGameName());
        assertEquals("Jeno",comments.get(0).getUserName());
        assertEquals("Lorem Ipsum",comments.get(0).getText());
        assertEquals(date,comments.get(0).getCommentedAt());
    }


    @Test
    public void testGetComments() throws SQLException, FileNotFoundException {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("Mines", "Palo", "Lorem Ipsum", date));
        //commentService.addComment(new Comment("Mines", "Peto", "Lorem Ipsum", date));
        commentService.addComment(new Comment("Mines", "Katka", "Lorem Ipsum", date));
        commentService.addComment(new Comment("Mines", "Zuzka", "Lorem Ipsum", date));
        commentService.addComment(new Comment("Mines", "Jergus", "Lorem Ipsum", date));
        commentService.addComment(new Comment("Mines", "Fero", "schcim????", date));
        commentService.addComment(new Comment("Tiles", "Jana", "Lorem Ipsum", date));

        var comments = commentService.getComments("Mines");
        assertEquals(5, comments.size());

        assertEquals("Mines",comments.get(0).getGameName());
        assertEquals("Palo",comments.get(0).getUserName());
        assertEquals("Lorem Ipsum",comments.get(0).getText());
        assertEquals(date,comments.get(0).getCommentedAt());

        assertEquals("Mines",comments.get(1).getGameName());
        assertEquals("Zuzka",comments.get(1).getUserName());
        assertEquals("Lorem Ipsum",comments.get(1).getText());
        assertEquals(date,comments.get(1).getCommentedAt());

        assertEquals("Mines",comments.get(2).getGameName());
        assertEquals("Katka",comments.get(2).getUserName());
        assertEquals("Lorem Ipsum",comments.get(2).getText());
        assertEquals(date,comments.get(2).getCommentedAt());

        assertEquals("Mines",comments.get(3).getGameName());
        assertEquals("Peto",comments.get(3).getUserName());
        assertEquals("Lorem Ipsum",comments.get(3).getText());
        assertEquals(date,comments.get(3).getCommentedAt());

        assertEquals("Mines",comments.get(4).getGameName());
        assertEquals("Jergus",comments.get(4).getUserName());
        assertEquals("Lorem Ipsum",comments.get(4).getText());
        assertEquals(date,comments.get(4).getCommentedAt());

    }



}