package sk.tuke.gamestudio.game.tiles.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TileHiScores {

    List<Map<Integer,String>> scores ;
    public TileHiScores() throws IOException {
        scores = new ArrayList<>();
        loadScores();
    }

    public void saveScore(int category, String name, int time) {
        scores.get(category).put(time,name);
    }

    //TODO split to separate class (or inteface to support some sql engine later?)
    public void loadScores() throws IOException {
        File f = new File("scores.csv");
//TODO fix fileNotFound
        Scanner reader = new Scanner(f);

        //3 normal categories,1 custom
        scores.add(new TreeMap<>());
        scores.add(new TreeMap<>());
        scores.add(new TreeMap<>());
        scores.add(new TreeMap<>());

        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split("\\|");
            scores.get(Integer.parseInt(data[0])).put(Integer.parseInt(data[2]),data[1]);
        }
        reader.close();
    }

    public void saveScores() throws IOException {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < scores.size(); i++) {
            int finalI = i;
            scores.get(i).forEach((integer, s) ->{
                content.append(finalI + "|" + s + "|" + integer + "\n");
            });
        }

        FileWriter fp = new FileWriter("scores.csv");
        fp.write(content.toString());
        fp.close();
    }

    public Map<Integer, String> getHiScores(int i) {
        return scores.get(i);
    }
}
