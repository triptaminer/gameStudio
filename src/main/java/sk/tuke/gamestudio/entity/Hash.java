package sk.tuke.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hash {
    @Id
    @Column(unique = true, length = 64, nullable = false)
    private String h1;

    @Column(length = 64, nullable = false)
    private String h2;

    @Column(length = 64, nullable = false)
    private String h3;

    public Hash() {
    }

    public Hash(String h1, String h2, String h3) {
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
    }

    public String getH1() {
        return h1;
    }

    public String getH2() {
        return h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }
}
