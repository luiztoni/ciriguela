package br.edu.ciriguela.attendance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AttendanceControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private AttendanceController controller;

	@MockBean
	private AttendanceRepository repository;

	@Test
	void givenIdWhenFindThenReturnAttendance() throws Exception {
		Attendance attendance = new Attendance();
		attendance.setUpvote(20);
		attendance.setText("Atendimento 10-12hs");
		given(repository.findById(any(Long.class))).willReturn(Optional.of(attendance));
		mockMvc.perform(get("/attendances/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.upvote").value(attendance.getUpvote()))
				.andExpect(jsonPath("$.text").value(attendance.getText()));
	}
}
