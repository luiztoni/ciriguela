package br.edu.ciriguela.student;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, ListCrudRepository<Student, Long> {
    Student findByEmail(String email);
}


