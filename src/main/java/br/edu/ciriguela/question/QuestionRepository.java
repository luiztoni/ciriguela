package br.edu.ciriguela.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long>, ListCrudRepository<Question, Long> {
    Page<Question> findByQuizId(Long id, Pageable pageable);
}
