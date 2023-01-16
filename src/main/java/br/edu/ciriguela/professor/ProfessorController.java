package br.edu.ciriguela.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService service;

    @GetMapping
    public ResponseEntity<?> index(@RequestParam int page, @RequestParam(defaultValue = "10", required = false) int size) {
        List<Professor> professors = service.index(page, size);
        if (professors == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        Professor professor = service.show(id);
        if (professor == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestHeader(name = "admin-token") String adminToken, @Valid @RequestBody Professor professor) {
        service.store(professor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestHeader(name = "admin-token") String adminToken, @RequestBody Professor professor) {
        service.update(professor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestHeader(name = "admin-token") String adminToken, @PathVariable int id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
