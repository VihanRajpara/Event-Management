package com.crud.dao;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "HOD")
public class hod {
	private String contact;
	private String dep;
	private String email;
	private String name;
	private String pass;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToMany(mappedBy = "hod", cascade = CascadeType.REMOVE)
    private Set<faculty> faculties = new HashSet<>();
	
	
	public Set<faculty> getFaculties() {
		return faculties;
	}

	public void setFaculties(Set<faculty> faculties) {
		this.faculties = faculties;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

}
