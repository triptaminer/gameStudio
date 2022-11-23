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

    private boolean isPlaying = true;
    private TileControls controls;
    @Autowired
    public GameStudioServices gss;

    @RequestMapping
    public String processUserInput(Integer row, Integer column) {

        if (TilesField == null) {
            try {
                startNewGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (row != null && column != null) {
            int direction = -1;

            if (isEmptyAround(row, column)) {
                direction=getDirection(row,column);
            }

            if (direction == -1) {
                System.err.println("Bad input");
                return "ERROR wrong input!";
            }

            TilesField.moveTile(row, column, direction);
        }

        return "tiles";
    }

    @RequestMapping("/new")
    public String newGame() throws IOException {
        startNewGame();
        return "tiles";
    }

    public void startNewGame() throws IOException {

        TilesField = new TileGame(gss);
        TilesField.setGameProperties(3, 3, 1);
        controls = new TileControls();

    }

    public String getHtmlField() {
        gss.setGameName("Tiles");

        StringBuilder sb = new StringBuilder();

        sb.append("<table id='field'>\n");
        int rowCount = TilesField.getRowCount();
        int colCount = TilesField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                int tile = TilesField.getTile(i, j);
                sb.append("<td>");

                String movable = (isEmptyAround(i, j)) ? "movable" : "fixed";
                String onclick = (isEmptyAround(i, j)) ? "onclick='switchTile(" + i + "," + j + ")'" : "";

                sb.append(tile > 0 ? "<div class='field filled " + movable + "' " + onclick + ">" + tile + "</div>"
                        : "<div class='field empty'></div>");

                sb.append("</td>");

            }
            sb.append("</tr>\n");

        }
        sb.append("</table>\n");


        return sb.toString();
    }

    private boolean isEmptyAround(int r, int c) {
        return (
                (isValidTile(r + 1, c) && (TilesField.getTile(r + 1, c) == 0)) ||
                        (isValidTile(r, c + 1) && (TilesField.getTile(r, c + 1) == 0)) ||
                        (isValidTile(r - 1, c) && (TilesField.getTile(r - 1, c) == 0)) ||
                        (isValidTile(r, c - 1) && (TilesField.getTile(r, c - 1) == 0))
        );
    }

    private int getDirection(int r,int c){
        int direction=0;
        if(isValidTile(r + 1, c) && (TilesField.getTile(r + 1, c) == 0)) direction= 2;
        if(isValidTile(r, c + 1) && (TilesField.getTile(r, c + 1) == 0)) direction= 3;
        if(isValidTile(r - 1, c) && (TilesField.getTile(r - 1, c) == 0)) direction= 0;
        if(isValidTile(r, c - 1) && (TilesField.getTile(r, c - 1) == 0)) direction= 1;
        return direction;
    }

    private boolean isValidTile(int r, int c) {
        return (r >= 0 && c >= 0 && r < TilesField.getRowCount() && c < TilesField.getColumnCount());
    }

    public List<Score> getBestScores() {
        try {
            return gss.scoreService.getBestScores("Tiles");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGameStatusMsg() {
        String status = "";

        switch (TilesField.getState()) {
            case SOLVED -> status = "You won! You get " + TilesField.computeScore();
            case PLAYING -> status = "Playing...";
        }

        return status;
    }


}
