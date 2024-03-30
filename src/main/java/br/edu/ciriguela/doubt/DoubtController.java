package br.edu.ciriguela.doubt;

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
@RequestMapping("/doubts")
public class DoubtController {

    @Autowired
    private DoubtRepository repository;

    private List<Doubt> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return repository.findAll(request).getContent();
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam int page, @RequestParam(defaultValue = "10", required = false) int size) {
        List<Doubt> doubts = getPage(page, size);
        if (doubts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Doubt>>(doubts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable long id) {
        return repository.findById(id)
                .map(doubt -> ResponseEntity.ok(doubt))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<?> getByProfessor(@PathVariable long id, Pageable pageable) {
        Page<Doubt> doubts = repository.findByProfessorId(id, pageable);
        return new ResponseEntity<Page<Doubt>>(doubts, HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getByStudent(@PathVariable long id, Pageable pageable) {
        Page<Doubt> doubts = repository.findByStudentId(id, pageable);
        return new ResponseEntity<Page<Doubt>>(doubts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody Doubt doubt) {
        repository.save(doubt);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Doubt doubt) {
        repository.save(doubt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

