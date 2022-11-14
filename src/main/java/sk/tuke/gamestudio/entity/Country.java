package sk.tuke.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, length = 128, nullable = false)
    private String country;

    public Country() {
    }


    public Country(String country) {
        this.country=country;
    }
}
