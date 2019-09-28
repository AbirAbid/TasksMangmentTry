package com.tasksmangment.pfa.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tasksmangment.pfa.models.Task;
import com.tasksmangment.pfa.models.TaskAffected;
import com.tasksmangment.pfa.models.User;
import com.tasksmangment.pfa.repositories.TaskAffectedRepository;
import com.tasksmangment.pfa.repositories.TaskRepository;
import com.tasksmangment.pfa.repositories.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TaskRestController {

	@Autowired
	TaskRepository taskRepository;
	@Autowired
	private TaskAffectedRepository taskAffectedRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/listTaskAffect", method = RequestMethod.GET)
	// @PreAuthorize("hasRole('MANAGER')")
	public List<TaskAffected> getTaskAffected() {
		try {
			return taskAffectedRepository.findAll();
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}
	
	
	@RequestMapping(value = "/listTaskAffect/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('MANAGER')")
	public void deleteTaskAffected(@PathVariable Long id) {
		try {
			taskAffectedRepository.deleteById(id);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());

		}
	}

	@RequestMapping(value = "/AffectedTask", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MANAGER')")
	public TaskAffected addTaskAffected(@RequestBody TaskAffected taskAffected) {
		try {
			return taskAffectedRepository.save(taskAffected);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	// @PreAuthorize("hasRole('MANAGER')")
	public List<Task> listTasks() {
		try {
			return taskRepository.findAll();
		 } catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
	public Optional<Task> getTaskById(@PathVariable("id") long id) {
		try {
			return taskRepository.findById(id);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/tasks/{username}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('MANAGER')")
	public Task addTask(@RequestBody Task task, 
						@PathVariable String username) {
		try {
			System.out.println("Add new Task");
			User userMng = userRepository.findByUsername(username);
			task.setUsermanger(userMng);
			return taskRepository.save(task);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)

	@PreAuthorize("hasRole('MANAGER') or hasRole('TECHNICIEN') or hasRole('INGENIEUR')")

	public Task updateTask(@RequestBody Task task, @PathVariable Long id) {
		try {
			task.setId(id);
			return taskRepository.saveAndFlush(task);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());
			return null;
		}
	}

	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('MANAGER')")
	public void deleteTask(@PathVariable Long id) {
		try {
			taskRepository.deleteById(id);
		} catch (Exception ex) {
			System.out.println("Exception " + ex.getMessage());

		}
	}
}
