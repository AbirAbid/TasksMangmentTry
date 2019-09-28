package com.tasksmangment.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasksmangment.pfa.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
