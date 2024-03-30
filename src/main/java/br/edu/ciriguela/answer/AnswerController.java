package br.edu.ciriguela.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository repository;

    private List<Answer> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index() {
        List<Answer> answers = getPage(1, 30);
        if (answers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Answer>>(answers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(answer -> ResponseEntity.ok(answer))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<?> getByQuestion(@PathVariable long id) {
        List<Answer> answers = repository.findByQuestionId(id);
        if (answers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Answer>>(answers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Answer answer) {
        repository.save(answer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Answer answer) {
        repository.save(answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

