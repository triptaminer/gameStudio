package sk.tuke.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, length = 32, nullable = false)
    private String occupation;

    public Occupation(String o) {
        occupation=o;
    }

    public String getOccupationName() {
        return occupation;
    }

    public Occupation() {
    }




}
