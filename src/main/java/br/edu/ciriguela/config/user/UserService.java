package br.edu.ciriguela.config.user;

import br.edu.ciriguela.config.admin.Admin;
import br.edu.ciriguela.config.admin.AdminRepository;
import br.edu.ciriguela.professor.Professor;
import br.edu.ciriguela.professor.ProfessorRepository;
import br.edu.ciriguela.student.Student;
import br.edu.ciriguela.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.isEnabled()) {
            return new User(admin.getEmail(), admin.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        User user;
        Student student = studentRepository.findByEmail(email);
        if (student != null) {
            var authorities = student.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getName())
            ).toList();
            user = new User(student.getEmail(), student.getPassword(), authorities);
        } else {
            Professor professor = professorRepository.findByEmail(email);
            if (professor == null) {
                throw new UsernameNotFoundException("User not found with email : " + email);
            }
            var authorities = professor.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getName())
            ).toList();
            user = new User(professor.getEmail(), professor.getPassword(), authorities);
        }
        return user;
    }
}

