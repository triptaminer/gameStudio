package sk.tuke.gamestudio.game.tiles.ui;

import java.util.HashMap;
import java.util.Map;

public class TileControls {

    Map<String, Integer> controls = new HashMap<>();

    public TileControls() {
        controls.put("w",0);
        controls.put("a",1);
        controls.put("s",2);
        controls.put("d",3);
        controls.put("up",0);
        controls.put("left",1);
        controls.put("down",2);
        controls.put("right",3);
    }

    public int translate(String input) {
        if(controls.get(input)!=null)
            return controls.get(input);
        else
            return -1;
    }

}
