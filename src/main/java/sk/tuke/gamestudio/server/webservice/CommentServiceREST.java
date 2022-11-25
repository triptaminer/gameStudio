package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.CommentService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceREST {
    @Autowired
    CommentService commentService;

    @Autowired
    GameStudioServices gss;

    @RequestMapping(method = RequestMethod.POST)
    void addComment(@RequestBody String[] gameComment) {
        try {
            gss.setGameName(gameComment[0]);
            gss.commentService.addComment(gss,gameComment[1]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // http://localhost:8080/api/Comment/mines
    @RequestMapping(value = "/{game}", method = RequestMethod.GET)
    List<Comment> getComments(@PathVariable String game) {
        try {
            return gss.commentService.getComments(game);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // http://localhost:8080/api/Comment?game=Mines
//    @RequestMapping
//    List<Comment> getBestComments(@RequestParam(name="game") String game) {
//        return commentService.getBestComments(game);
//    }
}
