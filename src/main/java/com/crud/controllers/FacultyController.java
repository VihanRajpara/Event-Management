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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.faculty;
import com.crud.dao.hod;
import com.crud.databse.DBConnection;
import com.crud.hibernet.HibernetConnection;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FacultyController {

	FacultyController() {
		System.out.println("-------------------------------------FacultyController-----------------------");
	}

	@RequestMapping(value = "/faculty/dashboard")
	public String AdminDashboard() {
		return "/views/faculty/dashboard.jsp";
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
		if (faculty != null) {
			if (faculty.getPass().equals(password)) {
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
}
