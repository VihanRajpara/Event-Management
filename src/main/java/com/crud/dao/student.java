package com.crud.dao;

import javax.persistence.*;

@Entity
@Table(name = "STUDENT")
public class student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String email;
	
	private String pass;
	
	private String dep;
	
	private String contact;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "faculty_id")
	private faculty faculty;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "hod_id")
	private hod hod;
	
	private String type;
	
	private String verify;
	
	private int sem;
	
	
	public int getSem() {
		return sem;
	}
	public void setSem(int sem) {
		this.sem = sem;
	}
	public String getVerify() {
		return verify;
	}
	public void setVerify(String verify) {
		this.verify = verify;
	}
	public hod getHod() {
		return hod;
	}
	public void setHod(hod hod) {
		this.hod = hod;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(faculty faculty) {
		this.faculty = faculty;
	}
	
}
