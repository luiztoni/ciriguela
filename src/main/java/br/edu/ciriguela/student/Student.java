package br.edu.ciriguela.student;

import br.edu.ciriguela.config.user.User;
import br.edu.ciriguela.lesson.Lesson;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Student extends User {

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_lesson",
            joinColumns = {@JoinColumn(name = "student_id") },
            inverseJoinColumns = {@JoinColumn(name = "lesson_id") })
    private Set<Lesson> lessons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}

