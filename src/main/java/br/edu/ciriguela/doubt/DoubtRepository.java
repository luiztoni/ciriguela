package br.edu.ciriguela.doubt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DoubtRepository extends PagingAndSortingRepository<Doubt, Long>, ListCrudRepository<Doubt, Long> {
    Page<Doubt> findByProfessorId(long id, Pageable pageable);

    Page<Doubt> findByStudentId(long id, Pageable pageable);
}
