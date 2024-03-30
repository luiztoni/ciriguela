package br.edu.ciriguela;

import br.edu.ciriguela.discipline.Discipline;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
	
	//https://spring.io/guides/gs/testing-web/
	
	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private String url;
	
    @BeforeEach
    void setup() {
        this.url = String.format("http://localhost:%d/", port);
		System.out.println("init port "+ port);
    }

    @AfterEach
    void tearDown() {
        
    }

	@Test
	public void createAndConsultNewDiscipline() throws Exception {
		Discipline discipline = new Discipline();
		discipline.setName("Historia");
		HttpEntity<Discipline> request = new HttpEntity<>(discipline);
		ResponseEntity<String> postResponse = this.restTemplate.postForEntity(this.url+"disciplines", request, String.class);
		ResponseEntity<String> getResponse = this.restTemplate.getForEntity(this.url+"disciplines?page=1", String.class);
		assertThat(getResponse.getBody()).isNotBlank();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Discipline> disciplines = objectMapper.readValue(getResponse.getBody(), new TypeReference<List<Discipline>>(){});
		assertEquals(1, disciplines.size());
		assertEquals(discipline.getName(), disciplines.get(0).getName());
		assertNotNull(disciplines.get(0).getId());
		assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
	}
}
