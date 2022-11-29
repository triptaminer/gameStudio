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

    //TODO: count points from games to calculate level
    private int points;

    //TODO: admin/mod 0 - user, 1 - premium, 2 - mod, 3 - subadmin, 4 - admin
    private int privilege;

    //TODO: banned
    boolean banned;

    //TODO: muted
    boolean muted;


    @ManyToOne
    @JoinColumn(name="Country.id", nullable = false)
    private Country country;


    @ManyToOne
    @JoinColumn(name="Occupation.id", nullable = false)
    private Occupation occupation;

    public Player() {
    }

    public Player(String name, String about, Country country, Occupation occupation) {
        this.id = id;
        this.name = name;
        this.about = about;
        level = 0;
        points = 0;
        privilege = 0;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

}
