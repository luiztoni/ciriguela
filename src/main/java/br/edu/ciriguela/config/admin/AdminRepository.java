package br.edu.ciriguela.config.admin;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
