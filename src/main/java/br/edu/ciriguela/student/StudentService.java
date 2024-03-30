package br.edu.ciriguela.student;

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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private List<Student> getPage(int page, int size) {
        PageRequest request = PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
        return studentRepository.findAll(request).getContent();
    }

    public boolean store(Student student) throws BusinessException {
        if (emailExist(student.getEmail())) {
            throw new BusinessException("User Already Exist!");
        }
        student.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return true;
    }

    public List<Student> index(int page, int size) {
        return getPage(page, size);
    }

    public boolean update(Student student) throws BusinessException {
        Student studentInDB = studentRepository.findByEmail(student.getEmail());
        if (studentInDB != null && studentInDB.getId() != student.getId()) {
            throw new BusinessException("User Already Exist!");
        }
        student.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
        if (student.getPassword() == null) {
            student.setPassword(studentRepository.findById(student.getId()).get().getPassword());
        }
        studentRepository.save(student);
        return true;
    }

    public Student show(long id) {
        return studentRepository.findById(id).get();
    }

    public boolean delete(long id) {
        if (!studentRepository.findById(id).isPresent())
            return false;
        studentRepository.deleteById(id);
        return true;
    }

    public boolean emailExist(String email) {
        return studentRepository.findByEmail(email) != null;
    }
}

