package com.alexandrenolla.backendtechforb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexandrenolla.backendtechforb.models.Task;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    
    List<Task> findByUser_Id(Long id);
}
