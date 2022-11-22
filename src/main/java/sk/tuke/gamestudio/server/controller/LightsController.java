package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.lights.core.LightsGame;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/lights")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LightsController {

    private LightsGame LightsField = null;

    private boolean isPlaying=true;

    @Autowired
    private GameStudioServices gss;

    @RequestMapping
    public String processUserInput(Integer row, Integer column){

        if (LightsField ==null)
            startNewGame();


        if(row!=null&&column!=null){
                LightsField.switchTile(row, column);
        }

        return "lights";
    }

    @RequestMapping("/new")
    public String newGame(){
        startNewGame();
        return "lights";
    }
    public void startNewGame(){
        //gss=new GameStudioServices();


        gss.setGameName("Lights");
        //scoreService =new ScoreServiceJPA();

            LightsField = new LightsGame(gss);
            LightsField.setGameProperties(5,5,1);


    }
    public String getHtmlField() {

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='field'>\n");
        int rowCount = LightsField.getRowCount();
        int colCount = LightsField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                Boolean tile = LightsField.getTile(i, j);
                sb.append("<td>");


                sb.append(tile?"<div class='field tile light' onclick='switchTile("+i+","+j+")'></div>"
                        :"<div class='field tile dark' onclick='switchTile("+i+","+j+")'></div>");


                sb.append("</td>");

            }
            sb.append("</tr>\n");

        }
        sb.append("</table>\n");


        return sb.toString();
    }

    public List<Score> getBestScores(){
        try {
            return gss.scoreService.getBestScores("Lights");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> getComments(){
        try {
            return gss.commentService.getComments("Lights");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float getAvgRating(){
        try {
            return gss.ratingService.getAvgRating("Lights");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGameStatusMsg(){
        String status="";

        switch (LightsField.getState()){
            case SOLVED -> status="You won! You get "+ LightsField.computeScore();
            case PLAYING -> status="Playing...";
        }

        return status;
    }

    public String getUserRating(){
        StringBuilder rating=new StringBuilder();

        Rating r=gss.ratingService.getRating(gss.currentPlayer,"Lights");

        if(r!=null){
            for (int i = 1; i < 6; i++) {
                if(i<=r.getValue()){
                    rating.append("<a href='?star="+i+"' class='starRated'>*</a>");
                }
                else{
                    rating.append("<a href='?star="+i+"' class='starBlank'>*</a>");
                }
            }

        }
        else{
            for (int i = 1; i < 6; i++) {
                    rating.append("<a href='?star="+i+"' class='starBlank'>*</a>");
            }
        }

        return rating.toString();
    }

}
