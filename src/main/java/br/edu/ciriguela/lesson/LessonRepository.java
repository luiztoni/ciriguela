package br.edu.ciriguela.lesson;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LessonRepository extends PagingAndSortingRepository<Lesson, Long>, ListCrudRepository<Lesson, Long> {

    List<Lesson> findByProfessorId(long id);

    List<Lesson> findByDisciplineId(long id);
}

