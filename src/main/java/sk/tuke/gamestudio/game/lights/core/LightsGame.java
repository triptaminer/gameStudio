package sk.tuke.gamestudio.game.lights.core;

import sk.tuke.gamestudio.game.mines.core.FieldState;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class LightsGame {

    private int rowCount;

    private int columnCount;

    private LightsFieldState state = LightsFieldState.PLAYING;

    private final Map<String, Boolean> tiles;

    private int userMoves;

    private final long startTime;


    private long actualTime;

    private int category;

    public LightsHiScores scores;

    public GameStudioServices GAME_STUDIO_SERVICES;

    public int getActualTime() {
        return (int) actualTime;
    }

    public void setGameProperties(int rowCount, int columnCount, int category) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.category = category;

        generate();
    }

    public LightsGame(GameStudioServices gss) throws IOException {

        tiles = new HashMap<String, Boolean>();
        userMoves = 0;
        startTime = currentTimeMillis();
        actualTime = 0;
        scores = new LightsHiScores();
        GAME_STUDIO_SERVICES=gss;

    }

    private void generate() {

        Random random = new Random();
        boolean value = false;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles.put(i + "x" + j, value);
            }
        }

        //shuffle
        int shuffleCount = 2;// :DDD
        //int shuffleCount=150;
        for (int i = 0; i < shuffleCount; i++) {
            switchLights(random.nextInt(rowCount), random.nextInt(columnCount));
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public LightsFieldState getState() {
        return state;
    }

    public Boolean getTile(int row, int column) {

        // System.out.println(row +"x" + column);
        if (isBetween(0, row, columnCount) && isBetween(0, column, columnCount)) {
            return tiles.get(row + "x" + column);
        } else {
            return false;
        }
    }

    public void switchTile(int row, int column) {
        if (!isBetween(0, row, rowCount) || !isBetween(0, column, columnCount)) {
            //TODO own exception
            System.err.println("cannot move there!");

        }
        switchLights(row, column);
        userMoves++;
        updateTimer();
        if (isSolved()) {
            state = LightsFieldState.SOLVED;
            GAME_STUDIO_SERVICES.processScore(GAME_STUDIO_SERVICES.getGameName(), GAME_STUDIO_SERVICES.getUserName(),computeScore());

        }
    }

    private void updateTimer() {
        long current = currentTimeMillis() / 1000;
        actualTime = current - startTime;
    }

    public String getTimer() {
        return niceTimer(actualTime * 1000);
    }

    public String niceTimer(long timestamp) {
        String t = new SimpleDateFormat("D:HH-mm:ss").format(new Date(timestamp));
        String minutes = t.split("-")[1];
        int days = Integer.parseInt(t.split("-")[0].split(":")[0]) - 1;
        String daysText = days > 0 ? days + "d " : "";

        int hours = Integer.parseInt(t.split("-")[0].split(":")[1]) - 1;//FIXME later: locale/timezones?
        String hoursText = hours > 0 ? hours + ":" : "";

        return daysText + hoursText + minutes;
    }

    private void switchLights(int row, int column) {
        changeLight(row, column);
        changeLight(row + 1, column);
        changeLight(row - 1, column);
        changeLight(row, column + 1);
        changeLight(row, column - 1);
    }

    private void changeLight(int row, int column) {
        if (isBetween(0, row, rowCount) && isBetween(0, column, columnCount)) {
            tiles.put(row + "x" + column, !getTile(row, column));
        }
    }

    private boolean isBetween(int min, int value, int max) {
        return (min <= value && value < max);
    }

    private boolean isSolved() {
        int totalCount = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (!tiles.get(i + "x" + j)) {
//                    System.out.println("dbg: "+i + "x" + j+"="+tiles.get(i + "x" + j));
                    totalCount++;
                }
            }
        }
//        System.out.println("dbg: " + totalCount + "=" + rowCount + "*" + columnCount);
        return totalCount == rowCount * columnCount;
    }

    public int getCategory() {
        return category;
    }

    public int computeScore() {
        int score = 0;
        if (state == LightsFieldState.SOLVED) {
            score = rowCount * columnCount * 10 -
                    (int) ((currentTimeMillis() - startTime) / 1000);
            if (score < 0) score = 0;
        }
        return score;
    }


}
