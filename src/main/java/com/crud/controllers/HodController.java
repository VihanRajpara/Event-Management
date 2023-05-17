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
import org.springframework.web.servlet.ModelAndView;

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
		System.out.println("--------------------------------HOD Controller--------------------------------");
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
		} else {
			res.sendRedirect("login");
		}
	}

	@RequestMapping(value = "/hod/logout")
	public void Logouthod(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.getSession().invalidate();
		String contextPath = req.getContextPath();
		res.sendRedirect(contextPath + "/login");
	}

	@RequestMapping(value = "/hod/approvalfaculty", method = RequestMethod.GET)
	public String Approvalfaculty(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
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
		if (fac_obj.getVerify().equals("Under Approval")) {
			fac_obj.setVerify("Approve");
		} else {
			fac_obj.setVerify("Under Approval");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) fac_obj);
		t.commit();
		session.close();
		res.sendRedirect("approvalfaculty");
	}

	@RequestMapping(value = "/hod/allstudent", method = RequestMethod.GET)
	public ModelAndView AllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sem", required = false) Integer sem) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM hod WHERE email = :email");
		query.setParameter("email", email);
		hod hod_obj = (hod) query.getSingleResult();
		List students;
		Query query2 = session.createQuery("FROM student WHERE hod_id =:dep AND verify = :verify");
		query2.setParameter("dep", hod_obj.getId());
		query2.setParameter("verify", "Approve");
		students = query2.list();

		session.close();
		ModelAndView mav = new ModelAndView("/views/hod/allstudent.jsp");
		mav.addObject("student_list", (Object) students);
		return mav;
	}
	
	@RequestMapping(value = "/hod/studentcr")
	public void Studentcr(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM student WHERE id = :id");
		query2.setParameter("id", (Object) id);
		student stu_obj = (student) query2.getSingleResult();
		if (stu_obj.getType().equals("Normal")) {
			stu_obj.setType("CR");
		} else {
			stu_obj.setType("Normal");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) stu_obj);
		t.commit();
		session.close();
		res.sendRedirect("allstudent");
	}
	
	@RequestMapping(value = "/hod/studentaccess")
	public void Studentaccess(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM student WHERE id = :id");
		query2.setParameter("id", (Object) id);
		student stu_obj = (student) query2.getSingleResult();
		if (stu_obj.getVerify().equals("Under Approval")) {
			stu_obj.setVerify("Approve");
		} else {
			stu_obj.setVerify("Under Approval");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) stu_obj);
		t.commit();
		session.close();
		res.sendRedirect("approvestudent");
	}
	
	@RequestMapping(value = "/hod/approvestudent", method = RequestMethod.GET)
	public String Approvalpage(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM hod WHERE email = :email");
		query.setParameter("email", email);
		hod hod_obj = (hod) query.getSingleResult();
		Query query2 = session.createQuery("FROM student WHERE hod_id = :dep AND verify = :verify");
		query2.setParameter("dep", hod_obj.getId());
		query2.setParameter("verify", "Under Approval");
		List students = query2.list();
		session.close();
		m.addAttribute("student_list", (Object) students);
		return "/views/hod/approvalstudent.jsp";
	}
	
	
	//------------------------------------------------------AJAX--------------------------------------------------------------------------------
	
		@RequestMapping(value = "/hod/newallstudent", method = RequestMethod.GET)
		public String  NewAllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
				@RequestParam(value = "sem", required = false) Integer sem,
				@RequestParam(value = "search", required = false) String searchQuery) throws IOException {
			String email = (String) req.getSession().getAttribute("email");
			System.out.println("     ============    " + sem);
			Session session = null;
			session = HibernetConnection.getSessionFactory().openSession();
			Query query = session.createQuery("FROM hod WHERE email = :email");
			query.setParameter("email", email);
			hod hod_obj = (hod) query.getSingleResult();
			List students;
			if(sem != null && searchQuery != null && !searchQuery.isEmpty()) {
				Query query2 = session.createQuery("FROM student WHERE hod_id = :facid AND verify = :verify AND sem = :sem AND name LIKE :search");
		        query2.setParameter("search", "%" + searchQuery + "%");
		        query2.setParameter("facid", hod_obj.getId());
		        query2.setParameter("sem", sem);
				query2.setParameter("verify", "Approve");
		        students = query2.list();
			}
			else if (searchQuery != null && !searchQuery.isEmpty()) {
				Query query2 = session.createQuery("FROM student WHERE hod_id = :facid AND verify = :verify AND name LIKE :search");
		        query2.setParameter("search", "%" + searchQuery + "%");
		        query2.setParameter("facid", hod_obj.getId());
				query2.setParameter("verify", "Approve");
		        students = query2.list();
		    }
			else if(sem != null) {
				Query query1 = session.createQuery("FROM student WHERE hod_id = :facid AND verify = :verify AND sem = :sem");
				query1.setParameter("facid", hod_obj.getId());
				query1.setParameter("verify", "Approve");
				query1.setParameter("sem", sem);
				students = query1.list();
			}else {
				Query query1 = session.createQuery("FROM student WHERE hod_id = :facid AND verify = :verify");
				query1.setParameter("facid", hod_obj.getId());
				query1.setParameter("verify", "Approve");
				students = query1.list();
			}
			 m.addAttribute("student_list", students);
			 return "/views/hod/ajax/newstudent.jsp";
		}
}
