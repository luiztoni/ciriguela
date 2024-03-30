package br.edu.ciriguela.option;

import br.edu.ciriguela.config.AbstractEntity;
import br.edu.ciriguela.question.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
public class Option extends AbstractEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Question question;

    private char item;

    private String text;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public char getItem() {
        return item;
    }

    public void setItem(char item) {
        this.item = item;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

