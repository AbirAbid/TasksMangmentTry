package com.tasksmangment.pfa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasksmangment.pfa.models.TaskAffected;
@Repository
public interface TaskAffectedRepository extends JpaRepository<TaskAffected, Long> {

}
