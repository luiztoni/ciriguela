package br.edu.ciriguela.professor;

import br.edu.ciriguela.config.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Professor extends User {

    @Column(nullable = false, length = 30)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

