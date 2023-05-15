package com.crud.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.faculty;
import com.crud.dao.hod;
import com.crud.dao.student;
import com.crud.hibernet.HibernetConnection;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {
	StudentController() {
		System.out.println("------------------------------Student Controller------------------------------");
	}

	@RequestMapping(value = "/student/dashboard")
	public String StudentDashboard() {
		return "/views/student/dashboard.jsp";
	}
	
	@RequestMapping(value = "/student/logout")
	public void LogoutStudent(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}

	@RequestMapping(value = "/student/loginaction", method = RequestMethod.POST)
	public void StudentloginAction(HttpServletRequest req, HttpServletResponse res, @RequestParam("password") String password,
			@RequestParam("type") String type, @RequestParam("email") String email) throws IOException, SQLException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM student WHERE email = :email");
		query.setParameter("email", email);
		student stu_obj = (student) query.getSingleResult();
		if(email.equals(stu_obj.getEmail()) && password.equals(stu_obj.getPass()) ) {
			req.getSession().setAttribute("email", email);
			req.getSession().setAttribute("type", type);
			req.getSession().setAttribute("verify", stu_obj.getVerify());
			res.sendRedirect("student/dashboard");
		}else {
			res.sendRedirect("login");
		}
	}

	@RequestMapping(value = "/student/signupaction", method = RequestMethod.POST)
	public void Signupaction(HttpServletRequest req, HttpServletResponse res, @RequestParam("hod") int hodId,
			@RequestParam("dep") String dep, @RequestParam("faculty") int facId, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("contact") String contact, @RequestParam("sem") int sem,
			@RequestParam("password") String password) throws IOException, SQLException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		hod hod_obj = session.get(hod.class, hodId);
		faculty fac_obj = session.get(faculty.class, facId);
		student student = new student();
		student.setHod(hod_obj);
		student.setFaculty(fac_obj);
		student.setDep(dep);
		student.setEmail(email);
		student.setName(name);
		student.setPass(password);
		student.setContact(contact);
		student.setSem(sem);
		student.setType("Normal");
		student.setVerify("Under Approval");
		Transaction t = session.beginTransaction();
		session.save(student);
		t.commit();
		session.close();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}
}
