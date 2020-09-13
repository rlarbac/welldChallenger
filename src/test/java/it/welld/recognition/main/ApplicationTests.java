package it.welld.recognition.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.welld.recognition.model.Point;
import it.welld.recognition.service.PlaneService;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlaneService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void addPoint() throws Exception {
		mockMvc.perform(post("/point")
			      .content(mapper.writeValueAsString(new Point(0.0, 1.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}	
	@Test
	public void getSpace() throws Exception {
		Point pA = new Point(0,0);
		service.addPoint(pA);
		
		mockMvc.perform(get("/space")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andDo(MockMvcResultHandlers.print());;
	}	
	
}
