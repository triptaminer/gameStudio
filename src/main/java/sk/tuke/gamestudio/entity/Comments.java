package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Comments {

    private int id;
    private String game;
    private String user;
    private String text;
    private Date commentedAt;

    public Comments() {
    }

    public Comments(int id, String game, String user, String text, Date commentedAt) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.text = text;
        this.commentedAt = commentedAt;
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
