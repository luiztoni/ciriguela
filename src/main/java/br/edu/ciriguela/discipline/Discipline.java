package br.edu.ciriguela.discipline;

import br.edu.ciriguela.config.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Schema(description = "Dados da disciplina")
public class Discipline extends AbstractEntity<Long> {

	@Schema(name = "name", description =  "Nome da disciplina", example = "Geografia")
    @Column(nullable = false, length = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
