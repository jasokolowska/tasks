package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testGetAllTasks() {
        //Given
        Task task1 = new Task("task 1", "content 1");
        Task task2 = new Task("task 2", "content 2");
        Task task3 = new Task("task 3", "content 3");
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        dbService.saveTask(task3);

        //When
        List<Task> tasksSaved = dbService.getAllTasks();

        //Then
        assertEquals(3, tasksSaved.size());

        //CleanUp
        dbService.deleteTask(task1.getId());
        dbService.deleteTask(task2.getId());
        dbService.deleteTask(task3.getId());
    }

    @Test
    void getTask() {
        //Given
        Task task1 = new Task("task 1", "content 1");
        dbService.saveTask(task1);

        //When
        Optional<Task> result = dbService.getTask(task1.getId());

        //Then
        assertEquals("task 1", result.get().getTitle());

        //CleanUp
        dbService.deleteTask(result.get().getId());

    }

}