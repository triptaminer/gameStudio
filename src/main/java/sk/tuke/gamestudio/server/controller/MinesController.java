package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/mines")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {

    private Field mineField = null;

    private boolean isPlaying=true;

    @Autowired
    public GameStudioServices gss;

    @RequestMapping
    public String processUserInput(Integer row, Integer column, String action) throws IOException {

        if (mineField==null)
            startNewGame();


        if(row!=null&&column!=null){
            if(action.equals("o")) {
                mineField.openTile(row, column);
            }
            else{
                mineField.markTile(row, column);
            }
        }

        //return getHtmlField();
        return "mines";
    }

    @RequestMapping("/new")
    public String newGame(){
        startNewGame();
        return "mines";
    }
    public void startNewGame(){
        try {
            mineField = new Field(8, 8, 2, gss);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getHtmlField() {
        gss.setGameName("Mines");

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='field'>\n");
        int rowCount = mineField.getRowCount();
        int colCount = mineField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                Tile tile = mineField.getTile(i, j);
                sb.append("<td>");


                switch (tile.getState()) {
                    case OPEN:
                        if (tile instanceof Clue) {
                            int v = ((Clue) tile).getValue();
                            if(v>0)
                            sb.append("<div class='field opened clue"+v+"'>"+v+"</div>");
                            else
                                sb.append("<div class='field opened'>&nbsp;</div>");

                        }
                        else {
                            sb.append("<div class='field mineOpened'></div>");
                        }
                        break;
                    case CLOSED:
                        sb.append("<div class='field closed' onclick='openTile("+i+","+j+")' oncontextmenu='markTile("+i+","+j+");return false;'></div>");
                        break;
                    case MARKED:
                        sb.append("<div class='field marked' oncontextmenu='markTile("+i+","+j+");return false;'></div>");
                        break;
                }
                sb.append("</td>");

            }
            sb.append("</tr>\n");

        }
        sb.append("</table>\n");


        return sb.toString();
    }

    public List<Score> getBestScores(){
        try {
            return gss.scoreService.getBestScores("Mines");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGameStatusMsg(){
        String status="";

        switch (mineField.getState()){
            case FAILED -> status="You failed!";
            case SOLVED -> status="You won! You get "+mineField.computeScore();
            case PLAYING -> status="Playing...";
        }

        return status;
    }


}
