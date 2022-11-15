package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

//TODO: switch String username to Player user !!!
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "game", "userObject" }) })
public class Rank {

    @Id
    @GeneratedValue
    private int id;
    private String game;

    @ManyToOne
    @JoinColumn(name="Player.id", nullable = false)
    private Player userObject;
    @Column(columnDefinition = "INT CHECK (ranking between 1 and 5)")
    private int ranking;
    private Date playedAt;

    public Rank() {
    }

    public Rank(String game, Player userObject, int ranking, Date playedAt) {
//        this.id = id;
        this.game = game;
        this.userObject = userObject;
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
        return userObject.getName();
    }

    public void setUser(Player userObject) {
        this.userObject = userObject;
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
                    ", username='" + userObject.getName() + '\'' +
                    ", points=" + ranking +
                    ", playedAt=" + playedAt +
                    '}';
        }


    }
