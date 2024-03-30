package br.edu.ciriguela.config.role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}

