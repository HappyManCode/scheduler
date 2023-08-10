package me.happyman.scheduler.task;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public List<Task> getUserTasks(Principal principal) {
        return taskService.getUserTasks(principal.getName());
    }

    @PostMapping(path = "/create")
    public Task create(@RequestBody Task task) {
        return taskService.create(task);
    }

    @GetMapping(path = "/all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}
