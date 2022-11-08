package sk.tuke.gamestudio.game.tiles.core;

import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

public class TileGame {

    public final String gameName="Tiles";
    private int rowCount;

    private int columnCount;

    private TileFieldState state = TileFieldState.PLAYING;

    private final Map<String, Integer> tiles;

    private int userMoves;

    private final long startTime;

    public int getActualTime() {
        return (int) actualTime;
    }

    private long actualTime;

    private int category;

    public TileHiScores scores;
    public GameStudioServices GAME_STUDIO_SERVICES;

public void setGameProperties(int rowCount, int columnCount, int category){
    this.rowCount = rowCount;
    this.columnCount = columnCount;
    this.category=category;

    generate();
}

    public TileGame(GameStudioServices gss) throws IOException {

        tiles = new HashMap<String, Integer>();
        userMoves=0;
        startTime=currentTimeMillis();
        GAME_STUDIO_SERVICES=gss;
        //actualTime=0;
        //scores=new TileHiScores();

    }

    private void generate() {

        Random random = new Random();
        int value = 1;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                //tiles[i][j] = new Tile(value);
                if (value != rowCount * columnCount) {
                    tiles.put(i + "x" + j, value);
                    value++;
                }
            }
        }
        tiles.put((rowCount - 1) + "x" + (columnCount - 1), 0);

        //shuffle
        int shuffleCount=2;// :DDD
        //int shuffleCount=150;
        for (int i = 0; i < shuffleCount; i++) {
            String[] empty=getEmpty().toString().replace("[","").replace("]","").split("x");
            moveShuffle(Integer.parseInt(empty[0]),Integer.parseInt(empty[1]));
        }
    }


    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public TileFieldState getState() {
        return state;
    }

    public int getTile(int row, int column) {

        // System.out.println(row +"x" + column);
        if (isBetween(0,row,columnCount)&&isBetween(0,column,columnCount)) {
            return tiles.get(row + "x" + column);
        }
        else{
            return 0;
        }
    }

    public void moveTile(int row, int column, int direction) {
        if(!move(row,column,direction)){
            //TODO own exception
            System.err.println("cannot move there!");

        }
        userMoves++;
        updateTimer();
        if (isSolved()) {
            state = TileFieldState.SOLVED;

            GAME_STUDIO_SERVICES.processScore(GAME_STUDIO_SERVICES.getGameName(), GAME_STUDIO_SERVICES.getUserName(),computeScore());

            //scores.saveScore(category,);
        }
    }

    private void updateTimer() {
        long current = currentTimeMillis()/1000;
        actualTime=current-startTime;
    }

    public String getTimer(){
    return niceTimer(actualTime*1000);
    }
    public String niceTimer(long timestamp){
        String t= new SimpleDateFormat("D:HH-mm:ss").format(new Date(timestamp));
        String minutes=t.split("-")[1];
        int days=Integer.parseInt(t.split("-")[0].split(":")[0])-1;
        String daysText=days>0? days +"d ":"";

        int hours=Integer.parseInt(t.split("-")[0].split(":")[1])-1;//FIXME later: locale/timezones?
        String hoursText=hours>0? hours +":":"";

        return daysText+hoursText+minutes;
    }

    public void moveShuffle(int row, int column) {
        Random random=new Random();
        boolean success=false;
        do{
            success=move(row,column,random.nextInt(4));
        }
        while(!success);
    }
    private boolean move(int row, int column, int direction){
        int r2 = 0;
        int c2 = 0;

        switch (direction) {
            case 0 -> {//up
                r2 = row - 1;
                c2 = column;
            }
            case 1 -> {//left
                r2 = row;
                c2 = column - 1;
            }
            case 2 -> {//down
                r2 = row + 1;
                c2 = column;
            }
            case 3 -> {//left
                r2 = row;
                c2 = column + 1;
            }
        }

        return swapTiles(row, column, r2, c2);

    }

    private boolean swapTiles(int r1, int c1, int r2, int c2) {
        if (isBetween(0, r2, rowCount) && isBetween(0, c2, columnCount)) {
            int tempValue = getTile(r1, c1);
            tiles.put(r1 + "x" + c1, getTile(r2, c2));
            tiles.put(r2 + "x" + c2, tempValue);
            return true;
        } else {
            return false;
        }
    }

    private boolean isBetween(int min, int value, int max) {
        return (min <= value && value < max);
    }

    private boolean isSolved() {
    //FIXME: position zero tile at the end
        int totalCount = 1;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (tiles.get(i + "x" + j) == totalCount) {
                    totalCount++;
                }
            }
        }
//erte        if (tiles.get(rowCount-1 + "x" + columnCount-1) == totalCount)
 //       System.out.println(totalCount + "=" + rowCount + "*" + columnCount);
        return totalCount == rowCount * columnCount;
    }

    public Set<String> getEmpty() {
        return getKeysByValue(tiles, 0);
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public int getCategory() {
        return category;
    }

    public int computeScore() {
        int score = 0;
        if (state == TileFieldState.SOLVED) {
            score = rowCount * columnCount * 10 -
                    (int) ((currentTimeMillis() - startTime) / 1000);
            if (score < 0) score = 0;
        }
        return score;
    }
}
