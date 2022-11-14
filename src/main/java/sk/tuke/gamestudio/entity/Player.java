package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 32, nullable = false, unique = true)
    private String name;

    @Column(length = 500, nullable = false)
    private String about;

    private int level;

    @ManyToOne
    @JoinColumn(name="Country.id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name="Occupation.id", nullable = false)
    private Occupation occupation;

    public Player() {
    }

    public Player(String name, String about, int level, Country country, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.level = level;
        this.country = country;
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
