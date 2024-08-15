package com.example.demo01task.service;

import com.example.demo01task.dto.TaskDTO;
import com.example.demo01task.entity.Task;
import com.example.demo01task.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskDTO taskDTO, String username) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setUsername(username);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskDTO taskDTO, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to update this task");
        }
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to delete this task");
        }
        taskRepository.delete(task);
    }

    public List<Task> getAllTasks(String username) {
        return taskRepository.findByUsername(username);
    }

    public List<Task> getPendingTasks(String username) {
        return taskRepository.findByUsernameAndCompleted(username, false);
    }

    public List<Task> getCompletedTasks(String username) {
        return taskRepository.findByUsernameAndCompleted(username, true);
    }

    public Task markTaskAsCompleted(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to complete this task");
        }
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    //para la paginacion

    public Page<Task> getAllTasksPaginated(String username, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByUsername(username, pageable);
    }

    public Page<Task> getPendingTasksPaginated(String username, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByUsernameAndCompleted(username, false, pageable);
    }

    public Page<Task> getCompletedTasksPaginated(String username, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findByUsernameAndCompleted(username, true, pageable);
    }

    // Método para obtener una tarea por ID
    public Task getTaskById(Long id, String username) {
        return taskRepository.findById(id)
                .filter(task -> task.getUsername().equals(username))
                .orElseThrow(() -> new RuntimeException("Task not found or you don't have permission to view it"));
    }
}
