package com.example.demo01task.repository;

import com.example.demo01task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsernameAndCompleted(String username, boolean completed);
    List<Task> findByUsername(String username);

    //agregando para la paginacion
    Page<Task> findByUsername(String username, Pageable pageable);

    Page<Task> findByUsernameAndCompleted(String username, boolean completed, Pageable pageable);

}
