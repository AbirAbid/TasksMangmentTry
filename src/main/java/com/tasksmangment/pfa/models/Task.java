package com.tasksmangment.pfa.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String title;
	private Date startDate;
	private Date endDate;
	private String etat;
	private String comment;
	private boolean affected;
	@ManyToOne
	// forign key
	@JoinColumn(name = "usermanger")
	private User usermanger;
	
	
	
	public User getUsermanger() {
		return usermanger;
	}

	public void setUsermanger(User usermanger) {
		this.usermanger = usermanger;
	}

	public boolean isAffected() {
		return affected;
	}

	public void setAffected(boolean affected) {
		this.affected = affected;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Task() {
		super();
	}

	public Task(String description, Date startDate, Date endDate, String etat) {
		super();
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.etat = etat;
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	
}
