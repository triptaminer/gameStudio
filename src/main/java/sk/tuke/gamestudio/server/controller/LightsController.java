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
import sk.tuke.gamestudio.game.lights.core.LightsFieldState;
import sk.tuke.gamestudio.game.lights.core.LightsGame;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.FieldState;
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
    public GameStudioServices gss;



    @RequestMapping
    public String processUserInput(Integer row, Integer column){

        if (LightsField ==null)
            startNewGame();

        if(row!=null && column!=null && LightsField.getState() == LightsFieldState.PLAYING){
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
        gss.setGameName("Lights");
            LightsField = new LightsGame(gss);
            LightsField.setGameProperties(5,5,1);
    }
    public String getHtmlField() {
        gss.setGameName("Lights");

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='field'>\n");
        int rowCount = LightsField.getRowCount();
        int colCount = LightsField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                Boolean tile = LightsField.getTile(i, j);
                sb.append("<td>");
                String tileSize="tile"+colCount;


                sb.append(tile?"<div class='field "+tileSize+" light' onclick='switchTile("+i+","+j+")'></div>"
                        :"<div class='field "+tileSize+" dark' onclick='switchTile("+i+","+j+")'></div>");


                sb.append("</td>");

            }
            sb.append("</tr>\n");

        }
        sb.append("</table>\n");


        return sb.toString();
    }


    public String getGameStatusMsg(){
        String status="";

        switch (LightsField.getState()){
            case SOLVED -> status="You won! You get "+ LightsField.computeScore();
            case PLAYING -> status="Playing...";
        }

        return status;
    }


//    @RequestMapping("/rate")
//    public String rate(int star){
//        try {
//            gss.ratingService.addRating(gss,star);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return "redirect:/lights";
//    }
//    @RequestMapping("/comment")
//    public String addComment(String text){
//        try {
//            gss.commentService.addComment(gss,text);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        return "redirect:/lights";
//    }

}
