package br.edu.ciriguela.answer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long>, ListCrudRepository<Answer, Long> {
    List<Answer> findByQuestionId(long id);
}

