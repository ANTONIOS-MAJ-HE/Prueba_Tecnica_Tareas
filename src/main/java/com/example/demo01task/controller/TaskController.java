package com.example.demo01task.controller;

import com.example.demo01task.dto.TaskDTO;
import com.example.demo01task.entity.Task;
import com.example.demo01task.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO, Authentication authentication) {
        return ResponseEntity.ok(taskService.createTask(taskDTO, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO, Authentication authentication) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO, authentication.getName()));
    }

    //marcar tarea como completado endpoint
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(taskService.markTaskAsCompleted(id, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getAllTasks(authentication.getName()));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getPendingTasks(authentication.getName()));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasks(Authentication authentication) {
        return ResponseEntity.ok(taskService.getCompletedTasks(authentication.getName()));
    }

    //paginacion

    @GetMapping("/paginated")
    public ResponseEntity<Page<Task>> getAllTasksPaginated(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.getAllTasksPaginated(authentication.getName(), page, size, sortBy, sortDir));
    }

    @GetMapping("/pending/paginated")
    public ResponseEntity<Page<Task>> getPendingTasksPaginated(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.getPendingTasksPaginated(authentication.getName(), page, size, sortBy, sortDir));
    }

    @GetMapping("/completed/paginated")
    public ResponseEntity<Page<Task>> getCompletedTasksPaginated(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(taskService.getCompletedTasksPaginated(authentication.getName(), page, size, sortBy, sortDir));
    }

    // Endpoint para obtener una tarea por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(taskService.getTaskById(id, authentication.getName()));
    }
}
