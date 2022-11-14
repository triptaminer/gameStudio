package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Occupation() {
    }




}
