package br.edu.ciriguela.config.role;

import br.edu.ciriguela.config.AbstractEntity;
import br.edu.ciriguela.professor.Professor;
import br.edu.ciriguela.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;


import java.util.Collection;

@Entity
public class Role extends AbstractEntity<Integer> {
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Professor> professors;

    @ManyToMany(mappedBy = "roles")
    private Collection<Student> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Collection<Professor> professors) {
        this.professors = professors;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

}


