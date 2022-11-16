package sk.tuke.gamestudio.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sk.tuke.gamestudio.game.mines.core.Clue;
import sk.tuke.gamestudio.game.mines.core.Field;
import sk.tuke.gamestudio.game.mines.core.Tile;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;

@Controller
@RequestMapping("/mines")
public class MinesController {

    private Field mineField = null;
    private GameStudioServices gss;

    @RequestMapping
    public String processUserInput() throws IOException {

        if(mineField==null){
            mineField = new Field(8,8,1,gss);

        }

        //return getHtmlField();
        return "mines";
    }


    public String getHtmlField(){

        StringBuilder sb = new StringBuilder();

sb.append("<table>\n");
int rowCount = mineField.getRowCount();
int colCount = mineField.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            sb.append("<tr>");
            for (int j = 0; j < colCount; j++) {
                Tile tile=mineField.getTile(i,j);
                sb.append("<td>");


                switch (tile.getState()) {
                    case OPEN:
                        if (tile instanceof Clue)
                            sb.append(String.valueOf(((Clue) tile).getValue()).replace("0"," "));
                        else
                            sb.append("X");
                        break;
                    case CLOSED:
                        sb.append("-");
                        break;
                    case MARKED:
                        sb.append("M");
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
