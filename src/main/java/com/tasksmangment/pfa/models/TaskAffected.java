package com.tasksmangment.pfa.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "taskaffected")
public class TaskAffected {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
	// forign key
	@JoinColumn(name = "USER_ID")
	private User user;

	/*@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;*/

	@ManyToOne
	@JoinColumn(name = "TASK_ID")
	private Task task;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}*/

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskAffected(User user, Role role, Task task) {
		super();
		this.user = user;
		//this.role = role;
		this.task = task;
	}

	public TaskAffected() {
		super();
	}

}
