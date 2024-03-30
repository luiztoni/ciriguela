package br.edu.ciriguela.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository repository;

    private List<Schedule> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index() {
        List<Schedule> schedules = getPage(1,40);
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(schedule -> ResponseEntity.ok(schedule))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<?> getByLesson(@PathVariable long id) {
        List<Schedule> schedules = repository.findByLessonId(id);
        if (schedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Schedule schedule) {
        repository.save(schedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Schedule schedule) {
        repository.save(schedule);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

