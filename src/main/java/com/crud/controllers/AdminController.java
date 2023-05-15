package com.crud.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.dep;
import com.crud.dao.hod;
import com.crud.databse.DBConnection;
import com.crud.hibernet.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

	AdminController() {
		System.out.println("-------------------------------Admin Controller-------------------------------");
	}

	@RequestMapping(value = "/admin/dashboard")
	public String AdminDashboard() {
		return "/views/admin/dashboard.jsp";
	}

	@RequestMapping(value = "/admin/loginaction", method = RequestMethod.POST)
	public void CheckAdmin(@RequestParam("type") String type, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		if (email.equals("admin@gmail.com") && password.equals("@dmin")) {
			req.getSession().setAttribute("email", email);
			req.getSession().setAttribute("type", type);
			res.sendRedirect("admin/dashboard");
		} else {
			res.sendRedirect("login");
		}
	}

	@RequestMapping(value = "/admin/logout")
	public void LogoutAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}

	@RequestMapping(value = "/admin/addhod")
	public String Addhod(Model m, HttpServletRequest req, HttpServletResponse res) {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM hod");
		Query query = session.createQuery("FROM dep");
		List hods = query2.list();
		List deps = query.list();
		session.close();
		m.addAttribute("hod_list", (Object) hods);
		m.addAttribute("dep_list", (Object) deps);
		return "/views/admin/addhod.jsp";
	}
	
	@RequestMapping(value = "/admin/adddep")
	public String Adddep(Model m, HttpServletRequest req, HttpServletResponse res) {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM dep");
		List deps = query2.list();
		session.close();
		m.addAttribute("dep_list", (Object) deps);
		return "/views/admin/adddep.jsp";
	}

	@RequestMapping(value = "/admin/addhodaction", method = RequestMethod.POST)
	public void Addhodaction(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("hod") hod hod)
			throws IOException, SQLException {
			Session session = HibernetConnection.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.save(hod);
			t.commit();
			session.close();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/addhod");
	}

	@RequestMapping(value = "/admin/addhod/delete")
	public void Addhoddelete(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException {

		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		hod hod_obj = new hod();
		hod_obj.setId(id);
		hod_obj.getFaculties().clear();
		Transaction t = session.beginTransaction();
		session.delete((Object) hod_obj);
		t.commit();
		session.close();
		String errorMessage = "User with email " + hod_obj.getEmail() + " Delete !";
		req.getSession().setAttribute("errorMessage", errorMessage);
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/addhod");
	}

	@RequestMapping(value = "/admin/addhod/edit")
	public String Addhodedit(Model m, HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException {
//		Session session = null;
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM hod WHERE id = :id");
		query2.setParameter("id", (Object) id);
		hod hod_obj = (hod) query2.getSingleResult();
		session.close();
		m.addAttribute("hod", (Object) hod_obj);
		return "/views/admin/edithod.jsp";
	}
	
	@RequestMapping(value = "/admin/adddep/edit")
	public String Adddepedit(Model m, HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException {
//		Session session = null;
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM dep WHERE id = :id");
		query2.setParameter("id", (Object) id);
		dep dep_obj = (dep) query2.getSingleResult();
		session.close();
		m.addAttribute("dep", (Object) dep_obj);
		return "/views/admin/editdep.jsp";
	}

	@RequestMapping(value = "/admin/edithodaction", method = RequestMethod.POST)
	public void Edithodaction(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("hod") hod hod)
			throws IOException, SQLException {
		Session session = HibernetConnection.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.saveOrUpdate((Object) hod);
			t.commit();
			session.close();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/addhod");
	}
	
	@RequestMapping(value = "/admin/editdepaction", method = RequestMethod.POST)
	public void Editdepaction(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dep") dep dep)
			throws IOException, SQLException {
			Session session = null;
			session = HibernetConnection.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.saveOrUpdate((Object) dep);
			t.commit();
			session.close();
		
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/adddep");
	}
	@RequestMapping(value = "/admin/adddepaction", method = RequestMethod.POST)
	public void Adddepaction(HttpServletRequest req, HttpServletResponse res, @ModelAttribute("dep") dep dep)
			throws IOException, SQLException {
			Session session = null;
			session = HibernetConnection.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			session.save((Object) dep);
			t.commit();
			session.close();
		
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/adddep");
	}
	
	@RequestMapping(value = "/admin/adddep/delete")
	public void Adddepdelete(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException {

		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		dep dep_obj = new dep();
		dep_obj.setId(id);
		Transaction t = session.beginTransaction();
		session.delete((Object) dep_obj);
		t.commit();
		session.close();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/admin/adddep");
	}
}
