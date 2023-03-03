package com.example.todo.test.ws;

import static com.example.todo.ws.TodoConfiguration.WS_TODO_PATH;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todo.models.DetailedTodo;
import com.example.todo.services.TodoService;


@SpringBootTest
@AutoConfigureMockMvc
public class TodoWSTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private TodoService todoService;
	
	@Test
	public void testGetAllTodoOk() throws Exception {
		
		List<DetailedTodo> todos = Arrays.asList(
                new DetailedTodo(0, "Prepare food", false, "No description"),
                new DetailedTodo(1, "Do laundry", false, "No description"),
                new DetailedTodo(2, "Call the school principal", false, "No description")
        );
		
		 when(todoService.getAll()).thenReturn(todos);
		 
		this.mockMvc.perform(get(WS_TODO_PATH + "/all")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id", is(0)))
        .andExpect(jsonPath("$[0].title", is("Prepare food")))
        .andExpect(jsonPath("$[0].done", is(false)))
        .andExpect(jsonPath("$[1].id", is(1)))
        .andExpect(jsonPath("$[1].title", is("Do laundry")))
        .andExpect(jsonPath("$[1].done", is(false)))
        .andExpect(jsonPath("$[2].id", is(2)))
        .andExpect(jsonPath("$[2].title", is("Call the school principal")))
        .andExpect(jsonPath("$[2].done", is(false)));
	}
	
	@Test
    public void testUpdateTodoStateOk() throws Exception {
		String state = "true";
        mockMvc.perform(put(WS_TODO_PATH + "/0").param("state", state))
                .andExpect(status().isOk());

        verify(todoService, times(1)).updateTodoState(0, Boolean.valueOf(state));
    }
	
	@Test
    public void testGetTodoById() throws Exception {
        DetailedTodo todo = new DetailedTodo(1, "Go grocery shopping", false, "Milk, eggs, bread");
        
        when(todoService.getTodoById(1)).thenReturn(todo);

        mockMvc.perform(get(WS_TODO_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Go grocery shopping")))
                .andExpect(jsonPath("$.description", is("Milk, eggs, bread")))
                .andExpect(jsonPath("$.done", is(false)));
    }
	
	@Test
    public void testAddTodoOk() throws Exception {
        DetailedTodo todo = new DetailedTodo(0,"DoStuffbrain",false, "Nodescriptionneeded");
        String json = "{\"description\":\"Nodescriptionneeded\",\"done\":false,\"id\":0,\"title\":\"DoStuffbrain\"}";

        mockMvc.perform(post(WS_TODO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        verify(todoService, times(1)).addTodo(todo);
    }
	
	@Test
    public void testAddTodoWithEmptyTitle() throws Exception {
        DetailedTodo todo = new DetailedTodo(99,"",false, "");
        String json = "{\"description\":\"\",\"done\":false,\"id\":99,\"title\":\"\"}";

        mockMvc.perform(post(WS_TODO_PATH)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json))
	           .andExpect(status().isBadRequest());

        verify(todoService, never()).addTodo(todo);
    }
	
	@Test
    public void testAddTodoWithNullTitle() throws Exception {
        DetailedTodo todo = new DetailedTodo(99, null, false, "");
        String json = "{\"description\":\"\",\"done\":false,\"id\":99,\"title\":null}";

        mockMvc.perform(post(WS_TODO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());

        verify(todoService, never()).addTodo(todo);
    }
}
