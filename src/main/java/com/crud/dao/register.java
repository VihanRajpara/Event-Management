package com.crud.dao;

import javax.persistence.*;

@Entity
@Table(name="REGISTER")
public class register {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "event_id")
	private event event;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private student student;

	private String fee;
	
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public event getEvent() {
		return event;
	}

	public void setEvent(event event) {
		this.event = event;
	}

	public student getStudent() {
		return student;
	}

	public void setStudent(student student) {
		this.student = student;
	}
	
	
}
