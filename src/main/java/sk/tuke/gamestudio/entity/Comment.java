package sk.tuke.gamestudio.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Date;

//@Entity
public class Comment {

//    @Id
//    @GeneratedValue
    private int id;
    private String game;
    private String user;
    private String text;
    private Date commentedAt;

    public Comment() {
    }

    public Comment(String game, String user, String text, Date commentedAt) {

        this.id = id;
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
        return user;
    }

    public void setUserName(String user) {
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
