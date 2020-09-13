package it.welld.recognition;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.welld.recognition.model.Point;
import it.welld.recognition.service.PlaneService;

/**
 * My intention with this class is only to test the communication between the controller and REST call.
 * All the tests about the code/algorithm are already done.
 * @author Rodrigo
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;	

    @Autowired
    private PlaneService service;	
    
    @BeforeEach
    public void setUp() {
    	service.clearAll();
    }
    
	@Test
	public void addPoint() throws Exception {
		mockMvc.perform(post("/point")
			      .content(mapper.writeValueAsString(new Point(5.0, 1.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true)).andDo(MockMvcResultHandlers.print());

		mockMvc.perform(post("/point")
			      .content(mapper.writeValueAsString(new Point(5.0, 1.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(false)).andDo(MockMvcResultHandlers.print());
	
	}	
	@Test
	public void getSpace() throws Exception {

		mockMvc.perform(post("/point")
			      .content(mapper.writeValueAsString(new Point(5.0, 1.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());

	    mockMvc.perform(post("/point")
			      .content(mapper.writeValueAsString(new Point(3.0, 1.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());		

		mockMvc.perform(get("/space")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andDo(MockMvcResultHandlers.print());
	}	
	
	@Test
	public void deleteSpace() throws Exception {

		mockMvc.perform(delete("/space")).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}	
	
	@Test
	public void getLines() throws Exception {

		service.addPoint(new Point(0.0, 0.0));
		service.addPoint(new Point(-2.0, -2.0));
		service.addPoint(new Point(1.0, 0.0));

		mockMvc.perform(post("/point")
				  .content(mapper.writeValueAsString(new Point(2.0, 2.0)))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());		

		mockMvc.perform(get("/lines/{n}", 1)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andDo(MockMvcResultHandlers.print());
		mockMvc.perform(get("/lines/{n}", 4)).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty()).andDo(MockMvcResultHandlers.print());
	}	
}
