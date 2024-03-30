package br.edu.ciriguela.schedule;

import br.edu.ciriguela.config.AbstractEntity;
import br.edu.ciriguela.lesson.Lesson;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
public class Schedule extends AbstractEntity<Long> {

    @Enumerated(EnumType.STRING)
    private Moment moment;

    @Enumerated(EnumType.STRING)
    private Day day;

    @Enumerated(EnumType.STRING)
    private Local local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;

    public Moment getMoment() {
        return moment;
    }

    public void setMoment(Moment moment) {
        this.moment = moment;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}

