package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private String game;

    @ManyToOne
    @JoinColumn(name="Player.id", nullable = false)

    private Player user;
    private String text;
    private Date commentedAt;

    public Comment() {
    }

    public Comment(String game, Player user, String text, Date commentedAt) {

        this.game = game;
        this.user = user;
        this.text = text;
        this.commentedAt = commentedAt;
    }

    public String getGameName() {
        return game;
    }

    public void setGameName(String game) {
        this.game = game;
    }

    public String getUserName() {
        return user.getName();
    }

    public void setUserName(Player user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }


}
