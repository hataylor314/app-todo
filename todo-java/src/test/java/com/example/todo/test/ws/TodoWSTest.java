package com.example.todo.test.ws;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static com.example.todo.ws.TodoConfiguration.WS_TODO_PATH;


@SpringBootTest
@AutoConfigureMockMvc
public class TodoWSTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void getAllTestOK() throws Exception {
		this.mockMvc.perform(get(WS_TODO_PATH + "/all")).andDo(print()).andExpect(status().isOk());
	}
	
}
