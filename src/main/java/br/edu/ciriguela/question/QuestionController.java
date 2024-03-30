package br.edu.ciriguela.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository repository;

    private List<Question> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam int page, @RequestParam(defaultValue = "10", required = false) int size) {
        List<Question> questions = getPage(page, size);
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(question -> ResponseEntity.ok(question))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getAllByQuiz(@PathVariable long id, Pageable pageable) {
        Page<Question> questions = repository.findByQuizId(id, pageable);
        return new ResponseEntity<Page<Question>>(questions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Question question) {
        repository.save(question);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Question question) {
        repository.save(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

