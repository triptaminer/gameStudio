package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "game", "Player.id" }) })
public class Rating {

    @Id
    @GeneratedValue
    private int id;
    private String game;

    @ManyToOne
    @JoinColumn(name="Player.id", nullable = false)
    private Player user;
    @Column(columnDefinition = "INT CHECK (value between 1 and 5)")
    private int value;
    private Date ratedAt;

    public Rating() {
    }

    public Rating(String game, Player user, int value, Date ratedAt) {
//        this.id = id;
        this.game = game;
        this.user = user;
        this.value = value;
        this.ratedAt = ratedAt;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUsername() {
        return user.getName();
    }

    public void setUser(Player userObject) {
        this.user = userObject;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(Date playedAt) {
        this.ratedAt = playedAt;
    }

        @Override
        public String toString() {
            return "Score{" +
                    "game='" + game + '\'' +
                    ", username='" + user.getName() + '\'' +
                    ", points=" + value +
                    ", playedAt=" + ratedAt +
                    '}';
        }


    public Player getUser() {
        return user;
    }
}
