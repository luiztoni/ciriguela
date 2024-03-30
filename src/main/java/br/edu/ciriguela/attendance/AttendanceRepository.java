package br.edu.ciriguela.attendance;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AttendanceRepository extends PagingAndSortingRepository<Attendance, Long>, ListCrudRepository<Attendance, Long> {
    List<Attendance> findByProfessorId(long id);    
}

