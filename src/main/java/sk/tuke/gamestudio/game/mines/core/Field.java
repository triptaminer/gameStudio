package sk.tuke.gamestudio.game.mines.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import static java.lang.System.currentTimeMillis;
import sk.tuke.gamestudio.GameStudioConsole.*;
import sk.tuke.gamestudio.services.GameStudioServices;

public class Field {

    public final String gameName="Mines";

    private final int rowCount;

    private final int columnCount;

    private final int mineCount;

    private FieldState state = FieldState.PLAYING;

    private final Tile[][] tiles;

    private int openCount;

    private int moves;

    private long start;


    public GameStudioServices GAME_STUDIO_SERVICES;
    private int score;


    public Field(int rowCount, int columnCount, int mineCount) throws IOException {
        if (rowCount * columnCount <= mineCount)
            throw new IllegalArgumentException("Invalid number of mines in the field");

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];
        moves = 0;
        generate();
        start = currentTimeMillis();
        //GAME_STUDIO_SERVICES=gss;
    }

    private void generate() {
        generateMines();
        fillWithClues();
    }

    private void generateMines() {
        Random random = new Random();
        for (int storedMinesCount = 0; storedMinesCount < mineCount; ) {
            int row = random.nextInt(rowCount);
            int column = random.nextInt(columnCount);
            if (tiles[row][column] == null) {
                tiles[row][column] = new Mine();
                storedMinesCount++;
            }
        }
    }

    private void fillWithClues() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Tile tile = tiles[row][column];
                if (tile == null) {
                    tiles[row][column] = new Clue(countNeighbourMines(row, column));
                }
            }
        }
    }

    private int countNeighbourMines(int r, int c) {
        int count = 0;
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && i < rowCount && j >= 0 && j < columnCount) {
                    if (tiles[i][j] instanceof Mine) {
                        count++;
                    }
                }
            }

        }
        return count;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public FieldState getState() {
        return state;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public void markTile(int row, int column) {
        Tile tile = getTile(row, column);
        if (tile.getState() == TileState.CLOSED)
            tile.setState(TileState.MARKED);
        else if (tile.getState() == TileState.MARKED)
            tile.setState(TileState.CLOSED);
    }

    public void openTile(int row, int column) {
        Tile tile = getTile(row, column);
        if (tile.getState() == TileState.CLOSED) {
            tile.setState(TileState.OPEN);
            openCount++;
            addMove();

            if (tile instanceof Mine) {
                state = FieldState.FAILED;
                return;
            }
            Clue c = (Clue) getTile(row, column);
            if (c.getValue() == 0) {
                openNeighborTiles(row, column);
            }

            if (isSolved()) {
                //send score only once
                if(state!=FieldState.SOLVED) {
                    //GAME_STUDIO_SERVICES.processScore(GAME_STUDIO_SERVICES.getGameName(), computeScore());
                    score=computeScore();
                }
                state = FieldState.SOLVED;
            }
        }
    }

    private void openNeighborTiles(int r, int c) {
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && i < rowCount && j >= 0 && j < columnCount) {
                    openTile(i, j);
                }
            }
        }
    }

    private boolean isSolved() {
        return rowCount * columnCount - mineCount == openCount;
    }

    public void addMove() {
        this.moves++;
    }

    public long getActualTime() {
        return start;
    }

    public int computeScore() {
        int score = 0;
            score = rowCount * columnCount * 10 -
                    (int) ((currentTimeMillis() - start) / 1000);
            if (score < 0) score = 0;
        return score;
    }


    public void quit() {
        state = FieldState.FAILED;
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
    public int getScore() {
        return score;
    }


}
