package br.edu.ciriguela.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(attendance -> ResponseEntity.ok(attendance))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<?> getByProfessor(@PathVariable long id) {
        List<Attendance> attendances = repository.findByProfessorId(id);
        if (attendances.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(attendances, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Attendance attendance) {
        repository.save(attendance);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Attendance attendance) {
        repository.save(attendance);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

