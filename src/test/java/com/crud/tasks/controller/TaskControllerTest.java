package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(232L, "task1", "content 1"));
        taskDtos.add(new TaskDto(31L, "task2", "content 2"));
        taskDtos.add(new TaskDto(12L, "task3", "content 3"));
        taskDtos.add(new TaskDto(154L, "task4", "content 4"));

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(232L, "task1", "content 1"));
        tasks.add(new Task(31L, "task2", "content 2"));
        tasks.add(new Task(12L, "task3", "content 3"));
        tasks.add(new Task(154L, "task4", "content 4"));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    void shouldFetchSingleTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(232L, "task1", "content 1");
        Task task = new Task(232L, "task1", "content 1");
        long taskId = taskDto.getId();
        when(dbService.getTask(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("taskId", "232"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(232)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("task1")));
    }

    @Test
    void shouldDeleteTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(232L, "task1", "content 1");
        long taskId = taskDto.getId();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask")
                        .param("taskId", "232"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(232L, "task1", "content 1");
        Task task = new Task(232L, "task1", "content 1");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("task1")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(232L, "task1", "content 1");
        long taskId = taskDto.getId();

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}