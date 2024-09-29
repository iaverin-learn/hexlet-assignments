package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_EXTRA_FIELDS;


import static org.assertj.core.api.Assertions.assertThat;


import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    void testShow() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        task = taskRepository.save(task);
        System.out.println("Task" + task.getId());
        var result = mockMvc.perform(get("/tasks/{id}", task.getId())).andExpect(status().isOk())
            .andReturn();
        
        var body = result.getResponse().getContentAsString();
        var title = task.getTitle();

        assertThatJson(body).and(
                    a -> a.node("title").isEqualTo(title)
                    );
    }

    @Test
    void testCreate() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        
        System.out.println("Task" + task.getId());
        var result = mockMvc.perform(post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(task))
            ).andExpect(status().isCreated())
            .andReturn();
        
        var body = result.getResponse().getContentAsString();
        System.out.println(body);
        var title = task.getTitle();

        assertThatJson(body).and(
                    a -> a.node("title").isEqualTo(title)
                    );
    }

    @Test
    void testUpdate() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        task = taskRepository.save(task);
        
        var result = mockMvc.perform(put("/tasks/{id}", task.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(task))
            ).andExpect(status().isOk())
            .andReturn();
        
        var body = result.getResponse().getContentAsString();
        System.out.println(body);
        var title = task.getTitle();

        assertThatJson(body).and(
                    a -> a.node("title").isEqualTo(title)
                    );
    }

    @Test
    void testDelete() throws Exception {
        Task task = Instancio.of(Task.class).ignore(Select.field(Task::getId)).create();
        task = taskRepository.save(task);
        
        mockMvc.perform(delete("/tasks/{id}", task.getId()))
            .andExpect(status().isOk());
        assertThat(taskRepository.existsById(task.getId())).isFalse();
        
    }
    // END
}
