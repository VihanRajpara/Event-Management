package com.crud.controllers;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.*;
import com.crud.databse.*;
import com.crud.hibernet.HibernetConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
@Controller
public class HodController {
	HodController() {
		System.out.println("------------------HODController-------------------------");
	}

	@RequestMapping(value = "/hod/dashboard")
	public String AdminDashboard() {
		return "/views/hod/dashboard.jsp";
	}

	@RequestMapping(value = "/hod/loginaction", method = RequestMethod.POST)
	public void Checkhod(@RequestParam("type") String type, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest req, HttpServletResponse res)
			throws IOException, SQLException {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM hod where email=?");
		stmt.setString(1, email);
		ResultSet resultSet = stmt.executeQuery();
		if (resultSet.next()) {
			String dbpassword = resultSet.getString("pass");
			String dbemail = resultSet.getString("email");
			if (email.equals(dbemail) && password.equals(dbpassword)) {
				req.getSession().setAttribute("email", email);
				req.getSession().setAttribute("type", type);
				res.sendRedirect("hod/dashboard");
			} else {
				res.sendRedirect("login");
			}
		}
	}

	@RequestMapping(value = "/hod/logout")
	public void Logouthod(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}
	
	@RequestMapping(value = "/hod/approvalfaculty",method = RequestMethod.GET)
	public String Approvalfaculty(Model m,HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM hod WHERE email = :email");
		query.setParameter("email", email);
		hod hod_obj = (hod) query.getSingleResult();
		Query query2 = session.createQuery("FROM faculty WHERE hod_id = :hodid AND verify = :verify");
		query2.setParameter("hodid", hod_obj.getId());
		query2.setParameter("verify", "Under Approval");
		List faculties = query2.list();
		query2.setParameter("verify", "Approve");
		List afaculties = query2.list();
		session.close();
		m.addAttribute("faculty_list", (Object) faculties);
		m.addAttribute("afaculty_list", (Object) afaculties);
		return "/views/hod/approvefaculty.jsp";
	}
	
	@RequestMapping(value = "/hod/facultyaccess")
	public void Addhodedit(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM faculty WHERE id = :id");
		query2.setParameter("id", (Object) id);
		faculty fac_obj = (faculty) query2.getSingleResult();
		if(fac_obj.getVerify().equals("Under Approval")) {
			fac_obj.setVerify("Approve");
		}else {
			fac_obj.setVerify("Under Approval");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) fac_obj);
		t.commit();
		session.close();
		res.sendRedirect("approvalfaculty");
	}
}
