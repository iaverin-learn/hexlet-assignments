package exercise.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @GetMapping("")
    public List<TaskDTO> getTasks() {
        var tasks = taskRepository.findAll();
        List<TaskDTO> tasksOut = tasks.stream().map(taskMapper::map).collect(Collectors.toList());
        return tasksOut;
    }
    
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable long id, @RequestBody TaskUpdateDTO taskData) {
        Task task = taskRepository.findById(id).get();
        var assignee = userRepository.findById(taskData.getAssigneeId()).get();
        task.getAssignee().setTasks(null);
        task.setAssignee(assignee);
        taskMapper.update(taskData, task);
        task = taskRepository.save(task);
        return taskMapper.map(task);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable long id) {
        var task = taskRepository.findById(id).get();
        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable long id) {
        var task = taskRepository.findById(id).get();
        taskRepository.delete(task);
    }
    
    // END
}
