package jpa.playground;

import jpa.playground.entity.Student;
import jpa.playground.entity.StudyGroup;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class Playground {

    @PersistenceContext
     private EntityManager entityManager;

    @Bean
    public void play(){
        System.out.println("Starting playground");

/*
        entityManager.persist(new StudyGroup("Basic"));
        entityManager.persist(new StudyGroup("Advanced"));
        entityManager.persist(new StudyGroup("Expert"));
*/

        List<StudyGroup> studyGroups = entityManager.createQuery("select g from StudyGroup g").getResultList();

        int noOfGroups=studyGroups.size();

        System.out.println("groups: ");

        for (StudyGroup studyGroup:studyGroups) {
            System.out.println(studyGroup.getIdent()+" : "+studyGroup.getName());
        }

        System.out.println("Finishing playground");

        Student s=new Student("meno","priezvisko",studyGroups.get(1));

        entityManager.persist(s);

    }
}
