package sk.tuke.gamestudio.entity;

import sk.tuke.gamestudio.services.PlayerServiceJPA;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private int id;
    private String game;

    @ManyToOne
    @JoinColumn(name="Player.id", nullable = false)
    private Player user;
    private int points;
    private Date playedAt;

    public Score() {
    }

    public Score(String game, Player user, int points, Date playedAt) {
//        this.id = id;
        this.game = game;
        this.user = user;
        this.points = points;
        this.playedAt = playedAt;
    }

    public Score(String game, String string, int points, Timestamp timestamp) {
        //FIXME dirty workaround for now
        Player p=new PlayerServiceJPA().getPlayerByUsername(string);
        new Score(game, p, points, timestamp);
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Player getUser() {
        return user;
    }

    public String getUserName() {
        return user.getName();
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
        return "Score{" + "game='" + game + '\'' + ", username='" + user + '\'' + ", points=" + points + ", playedAt=" + playedAt + '}';
    }


}
