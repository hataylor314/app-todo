package com.example.todo.test.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.todo.entities.DetailedTodoEO;
import com.example.todo.exception.TodoException;
import com.example.todo.mappers.DetailedTodoMapper;
import com.example.todo.models.DetailedTodoDTO;
import com.example.todo.repositories.TodoRepository;
import com.example.todo.services.TodoService;


@RunWith(JUnit4.class)
public class TodoServiceTest {
	
	private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private DetailedTodoMapper detailedTodoMapper;

    @Before
    public void setup() {
    	 MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepository, detailedTodoMapper);
    }

    @Test
	public void testGetAll() {
		// Arrange
		List<DetailedTodoEO> todos = new ArrayList<>();
		todos.add(new DetailedTodoEO(1L, "Prepare food", false, "No description"));
		todos.add(new DetailedTodoEO(2L, "Do laundry", false, "No description"));
		todos.add(new DetailedTodoEO(3L, "Call the school principal", false, "No description"));
		
		when(todoRepository.getAll()).thenReturn(todos);
		
		List<DetailedTodoDTO> expected = new ArrayList<>();
		expected.add(new DetailedTodoDTO(1L, "Prepare food", false, "No description"));
		expected.add(new DetailedTodoDTO(2L, "Do laundry", false, "No description"));
		expected.add(new DetailedTodoDTO(3L, "Call the school principal", false, "No description"));
		
		when(detailedTodoMapper.convert(any(DetailedTodoEO.class))).thenReturn(new DetailedTodoDTO());
		
		// Act
		List<DetailedTodoDTO> actual = todoService.getAll();
		
		// Assert
		assertEquals(3, actual.size());
	}

    @Test
    public void testGetTodoById() throws TodoException {
        long id = 1L;

        DetailedTodoDTO detailedTodoDTO = mock(DetailedTodoDTO.class);
        DetailedTodoEO detailedTodoEO = mock(DetailedTodoEO.class);

        when(todoRepository.getTodoById(id)).thenReturn(detailedTodoEO);
        when(detailedTodoMapper.convert(detailedTodoEO)).thenReturn(detailedTodoDTO);

        DetailedTodoDTO result = todoService.getTodoById(id);

        assertNotNull(result);
    }

    @Test(expected = TodoException.class)
    public void testGetTodoByIdNotFound() throws TodoException {
        long id = 1L;

        when(todoRepository.getTodoById(id)).thenReturn(null);

        todoService.getTodoById(id);
    }

    @Test
    public void testUpdateTodoState() throws TodoException {
        long id = 1L;
        boolean done = true;

        DetailedTodoEO detailedTodoEO = mock(DetailedTodoEO.class);

        when(todoRepository.getTodoById(id)).thenReturn(detailedTodoEO);

        todoService.updateTodoState(id, done);

        // verify that the updateTodoState method of the repository was called with the correct parameters
        verify(todoRepository).updateTodoState(detailedTodoEO, done);
    }

    @Test(expected = TodoException.class)
    public void testUpdateTodoStateNotFound() throws TodoException {
        long id = 1L;
        boolean done = true;

        when(todoRepository.getTodoById(id)).thenReturn(null);

        todoService.updateTodoState(id, done);
    }

    @Test
    public void testAddTodo() {
        DetailedTodoDTO detailedTodoDTO = mock(DetailedTodoDTO.class);
        DetailedTodoEO detailedTodoEO = mock(DetailedTodoEO.class);

        when(detailedTodoMapper.convert(detailedTodoDTO)).thenReturn(detailedTodoEO);

        todoService.addTodo(detailedTodoDTO);

        // verify that the addTodo method of the repository was called with the correct parameter
        verify(todoRepository).addTodo(detailedTodoEO);
    }
	
}
