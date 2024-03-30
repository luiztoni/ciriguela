package br.edu.ciriguela.professor;

import br.edu.ciriguela.config.exception.BusinessException;
import br.edu.ciriguela.config.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private List<Professor> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return professorRepository.findAll(request).getContent();
    }

    public List<Professor> index(int page, int size) {
        return getPage(page, size);
    }

    public boolean store(Professor professor) throws BusinessException {
        if (emailExist(professor.getEmail())) {
            throw new BusinessException("User Already Exist!");
        }
        professor.setRoles(Arrays.asList(roleRepository.findByName("ROLE_PROFESSOR")));
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        professorRepository.save(professor);
        return true;
    }

    public boolean update(Professor professor) throws BusinessException {
        Professor professorInDB = professorRepository.findByEmail(professor.getEmail());
        if (professorInDB != null &&  professorInDB.getId() != professor.getId()) {
            throw new BusinessException("User Already Exist!");
        }
        professor.setRoles(Arrays.asList(roleRepository.findByName("ROLE_PROFESSOR")));
        if (professor.getPassword() == null) {
            professor.setPassword(professorRepository.findById(professor.getId()).get().getPassword());
        } else {
            professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        }
        professorRepository.save(professor);
        return true;
    }

    public boolean delete(long id) {
        if (!professorRepository.findById(id).isPresent())
            return false;
        professorRepository.deleteById(id);
        return true;
    }

    public Professor show(long id) {
        return professorRepository.findById(id).get();
    }

    public boolean emailExist(final String email) {
        return professorRepository.findByEmail(email) != null;
    }
}

