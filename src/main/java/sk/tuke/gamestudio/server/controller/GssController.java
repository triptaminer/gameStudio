package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)

public class GssController {

    @Autowired
    private GameStudioServices gss;


    public List<Score> getBestScores(){
        try {
            return gss.scoreService.getBestScores(gss.getGameName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getComments(){
        try {
            return gss.commentService.getComments(gss.getGameName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public float getAvgRating(){
        try {
            return gss.ratingService.getAvgRating(gss.getGameName());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public String getUserRating(){
        StringBuilder rating=new StringBuilder();

        Rating r=gss.ratingService.getRating(gss.currentPlayer,gss.getGameName());

        if(r!=null){
            for (int i = 1; i < 6; i++) {
                if(i<=r.getValue()){
                    rating.append("<a href='/rate?star="+i+"' class='starRated'>*</a>");
                }
                else{
                    rating.append("<a href='/rate?star="+i+"' class='starBlank'>*</a>");
                }
            }

        }
        else{
            for (int i = 1; i < 6; i++) {
                rating.append("<a href='/rate?star="+i+"' class='starBlank'>*</a>");
            }
        }

        return rating.toString();
    }


    @RequestMapping("/rate")
    public String rate(int star){
        try {
            gss.ratingService.addRating(gss,star);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/"+gss.getGameName().toLowerCase();
    }
    @RequestMapping("/comment")
    public String addComment(String text){
        try {
            gss.commentService.addComment(gss,text);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/"+gss.getGameName().toLowerCase();
    }



}
