package com.crud.controllers;

import java.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.faculty;
import com.crud.dao.student;
import com.crud.dao.hod;
import com.crud.databse.DBConnection;
import com.crud.hibernet.HibernetConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FacultyController {

	FacultyController() {
		System.out.println("------------------------------Faculty Controller------------------------------");
	}

	@RequestMapping(value = "/faculty/dashboard")
	public String FacultyDashboard() {
		return "/views/faculty/dashboard.jsp";
	}
	
	@RequestMapping(value = "/faculty/approvalstudent",method = RequestMethod.GET)
	public String Approvalpage(Model m,HttpServletRequest req, HttpServletResponse res)throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM faculty WHERE email = :email");
		query.setParameter("email", email);
		faculty fac_obj = (faculty) query.getSingleResult();
		Query query2 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify");
		query2.setParameter("facid", fac_obj.getId());
		query2.setParameter("verify", "Under Approval");
		List students = query2.list();
		session.close();
		m.addAttribute("student_list", (Object) students);
		return "/views/faculty/approvestudent.jsp";
	}
	
	@RequestMapping(value = "/faculty/allstudent")
	public String AllStudent(Model m,HttpServletRequest req, HttpServletResponse res)throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM faculty WHERE email = :email");
		query.setParameter("email", email);
		faculty fac_obj = (faculty) query.getSingleResult();
		Query query2 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify AND type = :type");
		query2.setParameter("facid", fac_obj.getId());
		query2.setParameter("verify", "Approve");
		query2.setParameter("type", "Normal");
		List students = query2.list();
		query2.setParameter("type", "CR");
		List crs = query2.list();
		session.close();
		m.addAttribute("student_list", (Object) students);
		m.addAttribute("student_cr", (Object) crs);
		return "/views/faculty/allstudent.jsp";
	}

	@RequestMapping(value = "/faculty/logout")
	public void LogoutFaculty(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}

	@RequestMapping(value = "/faculty/signupaction", method = RequestMethod.POST)
	public void signupaction(HttpServletRequest req, HttpServletResponse res, @RequestParam("hod") int hodId,
			@RequestParam("contact") String contact, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("password") String password)
			throws IOException, SQLException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		hod hod_obj= session.get(hod.class, hodId);
		faculty faculty = new faculty();
		faculty.setContact(contact);
		faculty.setEmail(email);
		faculty.setPass(password);
		faculty.setDep(hod_obj.getDep());
		faculty.setHod(hod_obj);
		faculty.setName(name);
		faculty.setVerify("Under Approval");
		Transaction t = session.beginTransaction();
		session.save(faculty);
		t.commit();
		session.close();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}

	@RequestMapping(value = "/faculty/loginaction", method = RequestMethod.POST)
	public void loginaction(HttpServletRequest req, HttpServletResponse res, @RequestParam("type") String type,
			@RequestParam("email") String email, @RequestParam("password") String password)
			throws IOException, SQLException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM faculty WHERE email=:email");
		query.setParameter("email", email);
		faculty faculty = (faculty) query.uniqueResult();
		String cemail=faculty.getEmail();
		String cpasswprd=faculty.getPass();
		if (faculty != null) {
			if (email.equals(cemail) && password.equals(cpasswprd)) {
				req.getSession().setAttribute("email", email);
				req.getSession().setAttribute("type", type);
				req.getSession().setAttribute("verify", faculty.getVerify());
				res.sendRedirect("faculty/dashboard");
			}else {
				res.sendRedirect("login");
			}
		} else {
			res.sendRedirect("login");
		}

	}
	
	@RequestMapping(value = "/faculty/studentaccess")
	public void Studentaccess(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM student WHERE id = :id");
		query2.setParameter("id", (Object) id);
		student stu_obj = (student) query2.getSingleResult();
		if(stu_obj.getVerify().equals("Under Approval")) {
			stu_obj.setVerify("Approve");
		}else {
			stu_obj.setVerify("Under Approval");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) stu_obj);
		t.commit();
		session.close();
		res.sendRedirect("approvalstudent");
	}
	
	@RequestMapping(value = "/faculty/studentcr")
	public void Studentcr(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM student WHERE id = :id");
		query2.setParameter("id", (Object) id);
		student stu_obj = (student) query2.getSingleResult();
		if(stu_obj.getType().equals("Normal")) {
			stu_obj.setType("CR");
		}else {
			stu_obj.setType("Normal");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) stu_obj);
		t.commit();
		session.close();
		res.sendRedirect("allstudent");
	}
}
