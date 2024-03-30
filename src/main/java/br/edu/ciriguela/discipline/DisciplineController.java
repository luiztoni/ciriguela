package br.edu.ciriguela.discipline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(description = "DisciplineController", name = "Gerenciamento de disciplinas")
@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    @Autowired
    private DisciplineRepository repository;

    private List<Discipline> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam int page, @RequestParam(defaultValue = "10", required = false) int size) {
        List<Discipline> disciplines = getPage(page, size);
        if (disciplines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Discipline>>(disciplines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca por uma disciplina")
    @ApiResponse(responseCode = "200" , description =  "Resultados encontrados com sucesso.", content = @Content(schema = @Schema(anyOf = Discipline.class)))
    @ApiResponse(responseCode = "204" , description =  "Não há resultado para a pesquisa.", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<?> show(@Parameter(hidden = false) @PathVariable long id) {
        return repository.findById(id)
                .map(discipline -> ResponseEntity.ok(discipline))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Discipline discipline) {
        repository.save(discipline);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Discipline discipline) {
        repository.save(discipline);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

