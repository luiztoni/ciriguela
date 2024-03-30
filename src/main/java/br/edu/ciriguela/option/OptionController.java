package br.edu.ciriguela.option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/options")
public class OptionController {

    @Autowired
    private OptionRepository repository;

    @GetMapping("/question/{id}")
    public ResponseEntity<?> getByQuestion(@PathVariable long id) {
        List<Option> options = repository.findByQuestionId(id);
        if (options.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Option>>(options, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(option -> ResponseEntity.ok(option))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Option option) {
        repository.save(option);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Option option) {
        repository.save(option);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

