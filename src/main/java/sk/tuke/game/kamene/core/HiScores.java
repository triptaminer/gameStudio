package sk.tuke.game.kamene.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HiScores {

    //private Map<Integer, String>[] scores= (Map<Integer, String>[]) new Array[3];
    //private List<Map<String,Integer>> scores = new HashMap<Map<String,Integer>>();
    Collection<Map<Integer,String>> scores = new TreeSet<Map<Integer,String>>();
    public HiScores() throws IOException {
        //scores = new TreeMap<Integer, String>[]{};
        //loadScores();
    }

    public void saveScore(int category, String name, int time) {
        scores.add(new HashMap<Integer,String >());

    }

    public void loadScores() throws IOException {
        File f = new File("scores.csv");
//TODO fix fileNotFound
        Scanner reader = new Scanner(f);
        String t[]= new String[3];
        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split("\\|");
            System.out.println(data);
            t[Integer.parseInt(data[0])]=data[1]+"|"+data[2];
        }
        for (int i = 0; i < 3; i++) {
            Map<Integer,String> category = new HashMap<>();
            for (String c : t) {
                category.put(Integer.parseInt(c.split("\\|")[0]),c.split("\\|")[1]);
            }
            scores.add(category);
        }
        reader.close();
    }

    public void saveScores() throws IOException {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 3; i++) {



            for (Object item : scores.toArray()) {
//                content.append(i + "|" + item.getKey() + "|" + item.getValue() + "|" + "\n");
            }
        }
        FileWriter fp = new FileWriter("scores.csv");
        fp.write(content.toString());
        fp.close();
    }

}
