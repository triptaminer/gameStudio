package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private int id;
    private String game;

    private String username;
    private int points;
    private Date playedAt;

    public Score() {
    }

    public Score(String game, String username, int points, Date playedAt) {
//        this.id = id;
        this.game = game;
        this.username = username;
        this.points = points;
        this.playedAt = playedAt;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
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

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Score{" + "game='" + game + '\'' + ", username='" + username + '\'' + ", points=" + points + ", playedAt=" + playedAt + '}';
    }


}
