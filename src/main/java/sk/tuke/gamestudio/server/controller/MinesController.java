package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.GameStudioServices;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;

@Controller
@RequestMapping("/mines")
public class MinesController {

    private Field mineField = null;
    private GameStudioServices gss;

    @RequestMapping
    public String processUserInput(Integer row, Integer column, String action) throws IOException {

        if (mineField == null) {
            gss=new GameStudioServices();
            mineField = new Field(8, 8, 10, gss);

        }

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


    public String getHtmlField() {

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
//                        sb.append("<a href='?row="+i+"&amp;column="+j+"&amp;action=o'><img class='tile' src='/img/tile.PNG' alt='[ ]' width='32' height='32'/></a>");
//                        sb.append("<img class='tile' src='/img/tile.PNG' alt='[ ]' width='32' height='32' onclick='open("+i+","+j+")' oncontextmenu='mark("+i+","+j+")' contextmenu='mymenu'/>");
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
}
