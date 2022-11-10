package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    private String game;
    private String userName;
    private String text;
    private Date commentedAt;

    public Comment() {
    }

    public Comment(String game, String userName, String text, Date commentedAt) {

        this.game = game;
        this.userName = userName;
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
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
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
