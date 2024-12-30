package br.edu.ciriguela.professor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ciriguela.config.exception.BusinessException;
import br.edu.ciriguela.config.role.RoleRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

	@InjectMocks
	private ProfessorService service;
	
    @MockitoBean
    private ProfessorRepository professorRepository;

    @MockitoBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockitoBean
    private RoleRepository roleRepository;
    
	private ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);

    @Test
	void givenProfessorInDatabaseWhenStoreThenThrowsBusinessException() {
    	Professor professor = new Professor();
		String email = "email@email.com";
		professor.setEmail(email);
    	given(professorRepository.findByEmail(anyString())).willReturn(professor);
    	Exception exception = assertThrows(BusinessException.class, () -> { service.store(professor); });
    	verify(professorRepository, times(1)).findByEmail(emailCaptor.capture());
    	verify(professorRepository, times(0)).save(any(Professor.class));
    	assertNotNull(exception.getMessage());
		String emailValue = emailCaptor.getValue();
		assertEquals(email, emailValue);
    	then(exception.getMessage()).contains("User Already Exist!");    	
    }

	@Test
	void givenNewProfessorWhenStoreThenPersistInDatabase() {
		Professor professor = new Professor();
		professor.setEmail("email@email.com");
		given(professorRepository.findByEmail(anyString())).willReturn(null);
		boolean stored = service.store(professor);
		verify(professorRepository, times(1)).findByEmail(anyString());
		verify(professorRepository, times(1)).save(any(Professor.class));
		assertTrue(stored);
	}
}
