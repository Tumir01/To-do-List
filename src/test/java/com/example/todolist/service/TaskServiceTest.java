package com.example.todolist.service;

import com.example.todolist.controller.TaskController;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks() {
        Task task = new Task("Test Task", "Test Description", LocalDate.now());
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        var tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void addTask() {
        Task task = new Task("Test Task", "Test Description", LocalDate.now());
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.addTask(task);
        assertNotNull(savedTask);
        assertEquals("Test Task", savedTask.getTitle());

    }

    @Test
    void updateTask() {
        Task existingTask = new Task("Old Title", "Old Description", LocalDate.now());
        existingTask.setId(1L);
        Task updatedTask = new Task("New Title", "New Description", LocalDate.now());
        updatedTask.setId(2L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
    }

    @Test
    void updateTaskNotFound() {
        Task updatedTask = new Task("New Title", "New Description", LocalDate.now());
        updatedTask.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.updateTask(1L, updatedTask));
    }

    @Test
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}