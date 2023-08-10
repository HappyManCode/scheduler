package me.happyman.scheduler.task;

import lombok.AllArgsConstructor;
import me.happyman.scheduler.appuser.AppUser;
import me.happyman.scheduler.appuser.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository userRepository;

    public Task create(Task task) {
        taskRepository.save(task);

        return task;
    }

    public List<Task> getUserTasks(String userName) {
        AppUser user = userRepository.findAppUserByEmail(userName).orElseThrow();

        return taskRepository.findAllByUserId(user.getId()).stream().toList();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
