package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.ScoreService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/mines")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {

    private Field mineField = null;

    private boolean isPlaying=true;

    @Autowired
    public GameStudioServices gss;

    @Autowired
    public UserController userController;

    @Autowired
    public ScoreService scoreService;

    @RequestMapping("/new")
    public String newGame(){
        startNewGame();
        return "mines";
    }

    @RequestMapping("/async")
    public String asyncMode(){
        if (!userController.isLogged())
            return "redirect:/welcome";
        startOrUpdateGame(null,null,"");
        return "minesAsync";
    }

    @RequestMapping(value="/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field newGameJson(){
        startNewGame();
        return mineField;
    }

    @RequestMapping
    public String processUserInput(Integer row, Integer column, String action) {
        if (!userController.isLogged())
            return "redirect:/welcome";

        startOrUpdateGame(row, column, action);

        //overwriting all old links to new async version
        //return "mines";
        return "redirect:/mines/async";
    }

    @RequestMapping(value="/json",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field processUserInputJson(Integer row, Integer column, String action) {

       startOrUpdateGame(row, column, action);

        return mineField;
    }

    public void startNewGame(){
        gss.setGameName("Mines");
        try {
            mineField = new Field(13, 13, 2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void startOrUpdateGame(Integer row, Integer column, String action) {
        if (mineField==null)
            startNewGame();

        FieldState stateBeforeMove =mineField.getState();

        if(row !=null && column !=null && mineField.getState() == FieldState.PLAYING){
            if(action.equals("o")) {
                mineField.openTile(row, column);
            }
            else{
                mineField.markTile(row, column);
            }
        }

        //write score
        if (mineField.getState()==FieldState.SOLVED && mineField.getState()!=stateBeforeMove){
            if(userController.isLogged()){
                try {
                    scoreService.addScore(new Score("Mines", userController.getLoggedUserObject(), mineField.getScore(), new Date()));
                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
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
                String tileSize="tile"+colCount;

                switch (tile.getState()) {
                    case OPEN:
                        if (tile instanceof Clue) {
                            int v = ((Clue) tile).getValue();
                            if(v>0)
                            sb.append("<div class='"+tileSize+" field opened clue"+v+"'>"+v+"</div>");
                            else
                                sb.append("<div class='"+tileSize+" field opened'>&nbsp;</div>");

                        }
                        else {
                            sb.append("<div class='"+tileSize+" field mineOpened'>*</div>");
                        }
                        break;
                    case CLOSED:
                        sb.append("<div class='"+tileSize+" field closed' onclick='openTile("+i+","+j+")' oncontextmenu='markTile("+i+","+j+");return false;'></div>");
                        break;
                    case MARKED:
                        sb.append("<div class='"+tileSize+" field marked' oncontextmenu='markTile("+i+","+j+");return false;'>!</div>");
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
