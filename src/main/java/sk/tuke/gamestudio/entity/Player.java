package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String about;
    private int level;
    private int countryId;
    private int occupationId;

    public Player() {
    }




}
