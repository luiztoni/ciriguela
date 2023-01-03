package br.edu.ciriguela.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private LessonRepository repository;

    private List<Lesson> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam int page, @RequestParam(defaultValue = "10", required = false) int size) {
        List<Lesson> lessons = getPage(page, size);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<?> getByProfessor(@PathVariable long id) {
        List<Lesson> lessons = repository.findByProfessorId(id);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
    }

    @GetMapping("/discipline/{id}")
    public ResponseEntity<?> getByDiscipline(@PathVariable long id) {
        List<Lesson> lessons = repository.findByDisciplineId(id);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(lesson -> ResponseEntity.ok(lesson))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Lesson lesson) {
        repository.save(lesson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Lesson lesson) {
        repository.save(lesson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
