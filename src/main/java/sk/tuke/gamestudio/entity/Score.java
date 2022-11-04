package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Score {

    private int id;
    private String game;
    private String user;
    private int points;
    private Date playedAt;

    public Score() {
    }

    public Score(int id, String game, String user, int points, Date playedAt) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.points = points;
        this.playedAt = playedAt;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }


}
