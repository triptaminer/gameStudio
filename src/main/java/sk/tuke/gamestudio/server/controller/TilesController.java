package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.tiles.core.TileGame;
import sk.tuke.gamestudio.game.tiles.ui.TileControls;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/tiles")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class TilesController {

    private TileGame TilesField = null;

    private boolean isPlaying=true;
    private TileControls controls;
    @Autowired
    private GameStudioServices gss;

    @RequestMapping
    public String processUserInput(Integer row, Integer column){

        if (TilesField ==null) {
            try {
                startNewGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if(row!=null&&column!=null){

            //int direction = controls.translate(line);
            int direction=1;


            if (direction == -1) {
                System.err.println("Bad input");
                return "ERROR wrong input!";
            }

            String emptyTile = TilesField.getEmpty().toString().replace("[", "").replace("]", "");

            //int row = Integer.parseInt(emptyTile.split("x")[0]);
            //int column = Integer.parseInt(emptyTile.split("x")[1]);



            TilesField.moveTile(row, column,direction);
        }

        return "tiles";
    }

    @RequestMapping("/new")
    public String newGame() throws IOException {
        startNewGame();
        return "tiles";
    }
    public void startNewGame() throws IOException {
        //gss=new GameStudioServices();

//        Player p=null;
//        try {
//            p = gss.playerService.getPlayerByUsername("viki");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        gss.currentPlayer=p;


        gss.setGameName("Tiles");
        //scoreService =new ScoreServiceJPA();

            TilesField = new TileGame(gss);
            TilesField.setGameProperties(3,3,1);
            controls = new TileControls();

    }
    public String getHtmlField() {

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='field'>\n");
        int rowCount = TilesField.getRowCount();
        int colCount = TilesField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                int tile = TilesField.getTile(i, j);
                sb.append("<td>");


                sb.append(tile>0?"<div class='field filled' onclick='switchTile("+i+","+j+")'>"+tile+"</div>"
                        :"<div class='field empty' onclick='switchTile("+i+","+j+")'></div>");


                sb.append("</td>");

            }
            sb.append("</tr>\n");

        }
        sb.append("</table>\n");


        return sb.toString();
    }

    public List<Score> getBestScores(){
        try {
            return gss.scoreService.getBestScores("Tiles");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGameStatusMsg(){
        String status="";

        switch (TilesField.getState()){
            case SOLVED -> status="You won! You get "+ TilesField.computeScore();
            case PLAYING -> status="Playing...";
        }

        return status;
    }


}
