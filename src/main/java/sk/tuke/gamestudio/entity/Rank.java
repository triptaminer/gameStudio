package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Rank {

    @Id
    @GeneratedValue
    private int id;
    private String game;
    private String username;
    private int ranking;
    private Date playedAt;

    public Rank() {
    }

    public Rank(String game, String username, int ranking, Date playedAt) {
//        this.id = id;
        this.game = game;
        this.username = username;
        this.ranking = ranking;
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

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public Date getRankedAt() {
        return playedAt;
    }

    public void setRankedAt(Date playedAt) {
        this.playedAt = playedAt;
    }

        @Override
        public String toString() {
            return "Score{" +
                    "game='" + game + '\'' +
                    ", username='" + username + '\'' +
                    ", points=" + ranking +
                    ", playedAt=" + playedAt +
                    '}';
        }


    }
