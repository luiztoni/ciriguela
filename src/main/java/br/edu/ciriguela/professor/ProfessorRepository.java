package br.edu.ciriguela.professor;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProfessorRepository  extends PagingAndSortingRepository<Professor, Long>, ListCrudRepository<Professor, Long> {
    Professor findByEmail(String email);

    List<Professor> findByNameLike(String name);
}

