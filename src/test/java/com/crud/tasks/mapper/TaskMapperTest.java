package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "first task", "empty");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals("first task", task.getTitle());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "task 1", "description"));
        tasks.add(new Task(2L, "task 2", "description"));
        tasks.add(new Task(3L, "task 3", "description"));

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertEquals(3, taskDtos.size());
        assertEquals("task 2", taskDtos.get(1).getTitle());
    }
}