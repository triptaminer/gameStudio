package jpa.playground.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudyGroup {
    @Id
    @GeneratedValue
    private long ident;

    @Column(length = 64, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "ident")
    private List<Student> students;

    public StudyGroup() {
    }

    public StudyGroup(String name) {
        this.name = name;
    }

    public StudyGroup(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public long getIdent() {
        return ident;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return name;
    }
}
