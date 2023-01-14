package br.edu.ciriguela.config.security;

import br.edu.ciriguela.config.admin.Admin;
import br.edu.ciriguela.config.admin.AdminRepository;
import br.edu.ciriguela.config.role.Role;
import br.edu.ciriguela.config.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@Value("${ciriguela.default.admin.email}")
	private String email;

	@Value("${ciriguela.default.admin.password}")
	private String password;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        createNewRole("ROLE_ADMIN");
        createNewRole("ROLE_PROFESSOR");
        createNewRole("ROLE_STUDENT");
        createNewAdmin(email, password);
        alreadySetup = true;
    }

    @Transactional
    private void createNewRole(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
    }

    @Transactional
    private void createNewAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            admin = new Admin();
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setEnabled(true);
            adminRepository.save(admin);
        }
    }
}

