package br.edu.ciriguela.option;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OptionRepository extends PagingAndSortingRepository<Option, Long>, ListCrudRepository<Option, Long> {
    List<Option> findByQuestionId(long id);
}

