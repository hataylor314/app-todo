package com.example.todo.test.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todo.exception.TodoException;
import com.example.todo.mappers.DetailedTodo2TodoMapper;
import com.example.todo.models.DetailedTodoDTO;
import com.example.todo.models.TodoDTO;
import com.example.todo.services.TodoService;
import com.example.todo.ws.controllers.TodoWS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@SpringBootTest
public class TodoWSTest {


    private MockMvc mockMvc;

    @InjectMocks
    private TodoWS subject;

    @Mock
    private TodoService todoService;

    @Mock
    private DetailedTodo2TodoMapper detailedTodo2TodoMapper;

    @Test
    void getAll_Success() throws JsonProcessingException, Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();
        String url= "/todo/all";

        Mockito.when(todoService.getAll()
        ).thenReturn(new ArrayList<DetailedTodoDTO>() );

        Mockito.when(detailedTodo2TodoMapper.convert(anyList())
        ).thenReturn(new ArrayList<TodoDTO>() );


        this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                .content((byte[]) null)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getById_Success() throws JsonProcessingException, Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();

        long param = 3l;
        String url= "/todo/"+param;

        when(todoService.getTodoById(param)).thenReturn(new DetailedTodoDTO());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                .content(jsonString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getById_KO() throws JsonProcessingException, Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();

        long param = 3l;
        String url= "/todo/"+param;

        when(todoService.getTodoById(param)).thenThrow(TodoException.class);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url)
                .content(jsonString(param))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void addTodo_Success() throws JsonProcessingException, Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();

        String url= "/todo/";

       DetailedTodoDTO detailedTodoDTO = new DetailedTodoDTO(1L, "Prepare food", false, "No description");

        doNothing().when(todoService).addTodo(detailedTodoDTO);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(jsonString(detailedTodoDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void updateTodoState_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();

        long id = 3l;
        boolean newState = true;
        String url = "/todo/" + id + "?state=" + newState;

        doNothing().when(todoService).updateTodoState(id, newState);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void updateTodoState_KO() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(subject).build();

        long id = 12345L;
        boolean state = true;
        String url = "/todo/" + id + "?state=" + state;

        doThrow(new TodoException("Modification Todo impossible: l''élement à modifier est introuvable", 404)).when(todoService).updateTodoState(id, state);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
    }




    private String jsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}