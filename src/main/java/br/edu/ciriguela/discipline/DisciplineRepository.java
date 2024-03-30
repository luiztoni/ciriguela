package br.edu.ciriguela.discipline;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DisciplineRepository extends PagingAndSortingRepository<Discipline, Long>, ListCrudRepository<Discipline, Long> {
    List<Discipline> findByNameLikeIgnoreCase(String name);
}

