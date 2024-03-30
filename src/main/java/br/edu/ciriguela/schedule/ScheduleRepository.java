package br.edu.ciriguela.schedule;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Long>, ListCrudRepository<Schedule, Long> {
    List<Schedule> findByLessonId(long id);
}


