package br.edu.ciriguela.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long>, ListCrudRepository<Quiz, Long> {
    Page<Quiz> findByLessonId(long id, Pageable pageable);
}
