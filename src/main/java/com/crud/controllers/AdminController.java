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
import org.springframework.web.servlet.ModelAndView;

import com.crud.dao.dep;
import com.crud.dao.event;
import com.crud.dao.faculty;
import com.crud.dao.hod;
import com.crud.dao.student;
import com.crud.databse.DBConnection;
import com.crud.hibernet.*;

import jakarta.servlet.ServletException;
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
	
	//----------------------------------------------HOD-----------------------------------------------------------------

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
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM hod WHERE id = :id");
		query2.setParameter("id", (Object) id);
		hod hod_obj = (hod) query2.getSingleResult();
		session.close();
		m.addAttribute("hod", (Object) hod_obj);
		return "/views/admin/edithod.jsp";
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
	
	//------------------------------------------------Department-----------------------------------------------------------
	
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
	
	@RequestMapping(value = "/admin/adddep/edit")
	public String Adddepedit(Model m, HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM dep WHERE id = :id");
		query2.setParameter("id", (Object) id);
		dep dep_obj = (dep) query2.getSingleResult();
		session.close();
		m.addAttribute("dep", (Object) dep_obj);
		return "/views/admin/editdep.jsp";
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

	
	//--------------------------------------------------Student------------------------------------------------------------------
	
	@RequestMapping(value = "/admin/student", method = RequestMethod.GET)
	public ModelAndView AllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sem", required = false) Integer sem) throws IOException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		List students;
		Query query1 = session.createQuery("FROM student WHERE verify = :verify");
		query1.setParameter("verify", "Approve");
		students = query1.list();
		Query query3 = session.createQuery("FROM student WHERE verify = :verify");
		query3.setParameter("verify", "Under Approval");
		List newstudents = query3.list();
		Query query2 = session.createQuery("FROM student WHERE verify = :verify AND type = :type");
		query2.setParameter("verify", "Approve");
		query2.setParameter("type", "CR");
		List crs = query2.list();
		session.close();
		ModelAndView mav = new ModelAndView("/views/admin/allstudent.jsp");
		mav.addObject("student_list", (Object) students);
		mav.addObject("student_cr", (Object) crs);
		mav.addObject("student_new", (Object) newstudents);
		return mav;
	}



	@RequestMapping(value = "/admin/studentcr")
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
		res.sendRedirect("student");
	}
	
	@RequestMapping(value = "/admin/studentaccess")
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
		res.sendRedirect("student");
	}
	
	
	//---------------------------------------------Faculty------------------------------------------
	
	
	@RequestMapping(value = "/admin/faculty", method = RequestMethod.GET)
	public ModelAndView AllFaculty(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query1 = session.createQuery("FROM faculty WHERE verify = :verify");
		query1.setParameter("verify", "Approve");
		List faculties = query1.list();
		query1.setParameter("verify", "Under Approval");
		List newfaculties = query1.list();
		session.close();
		ModelAndView mav = new ModelAndView("/views/admin/allfaculty.jsp");
		mav.addObject("faculty_list", (Object) faculties);
		mav.addObject("faculty_new", (Object) newfaculties);
		return mav;
	}

	
	@RequestMapping(value = "/admin/facultyaccess")
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
		res.sendRedirect("faculty");
	}
	
	//------------------------------------------------------------Event--------------------------------------------------------------------------------
	
	@RequestMapping(value = "/admin/newevent")
	public String AddEvent(Model m, HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM dep");
		List deps = query2.list();
		Query query = session.createQuery("FROM event WHERE permission =:per");
		query.setParameter("per","done");
		List event = query.list();
		Query query1 = session.createQuery("FROM event WHERE permission =:per");
		query1.setParameter("per","Under Approval");
		List events = query1.list();
		session.close();
		m.addAttribute("dep", deps);
		m.addAttribute("events", event);
		m.addAttribute("newevents", events);
		return "/views/admin/newevent.jsp";
	}
	
	@RequestMapping(value = "/admin/addevent", method = RequestMethod.POST)
	public void Addeventaction(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam("info") String info,
			@RequestParam("contact") String contact)
			throws IOException, ServletException {
		String email = (String) req.getSession().getAttribute("email");
		String mtype = (String) req.getSession().getAttribute("type");
		Session session = HibernetConnection.getSessionFactory().openSession();
		event event=new event();
		event.setContact(contact);
		event.setEmail(email);
		event.setInfo(info);
		event.setMtype(mtype);
		event.setName(name);
		event.setPermission("done");
		event.setStatus("On");
		event.setType(type);
		Transaction t = session.beginTransaction();
		t.commit();
		session.save(event);
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/admin/eventstatus")
	public void Eventstatus(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM event WHERE id = :id");
		query2.setParameter("id", (Object) id);
		event eve_obj = (event) query2.getSingleResult();
		if (eve_obj.getStatus().equals("On")) {
			eve_obj.setStatus("Off");
		} else {
			eve_obj.setStatus("On");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) eve_obj);
		t.commit();
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/admin/eventaccess")
	public void Eventeccess(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM event WHERE id = :id");
		query2.setParameter("id", (Object) id);
		event eve_obj = (event) query2.getSingleResult();
		if (eve_obj.getPermission().equals("done")) {
			eve_obj.setPermission("Under Approval");
		} else {
			eve_obj.setPermission("done");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) eve_obj);
		t.commit();
		session.close();
		res.sendRedirect("newevent");
	}

	// ------------------------------------------------------AJAX--------------------------------------------------------------------------------

	@RequestMapping(value = "/admin/newallstudent", method = RequestMethod.GET)
	public String NewAllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sem", required = false) Integer sem,
			@RequestParam(value = "search", required = false) String searchQuery) throws IOException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		List students;
		if (sem != null && searchQuery != null && !searchQuery.isEmpty()) {
			Query query2 = session
					.createQuery("FROM student WHERE verify = :verify AND sem = :sem AND name LIKE :search");
			query2.setParameter("search", "%" + searchQuery + "%");
			query2.setParameter("sem", sem);
			query2.setParameter("verify", "Approve");
			students = query2.list();
		} else if (searchQuery != null && !searchQuery.isEmpty()) {
			Query query2 = session.createQuery("FROM student WHERE verify = :verify AND name LIKE :search");
			query2.setParameter("search", "%" + searchQuery + "%");
			query2.setParameter("verify", "Approve");
			students = query2.list();
		} else if (sem != null) {
			Query query1 = session.createQuery("FROM student WHERE verify = :verify AND sem = :sem");
			query1.setParameter("verify", "Approve");
			query1.setParameter("sem", sem);
			students = query1.list();
		} else {
			Query query1 = session.createQuery("FROM student WHERE verify = :verify");
			query1.setParameter("verify", "Approve");
			students = query1.list();
		}
		m.addAttribute("student_list", students);
		return "/views/admin/ajax/studenttable.jsp";
	}
	
	@RequestMapping(value = "/admin/newallfaculty", method = RequestMethod.GET)
	public String NewAllFaculty(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "search", required = false) String searchQuery) throws IOException {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		List students;
		if (searchQuery != null && !searchQuery.isEmpty()) {
			Query query2 = session
					.createQuery("FROM faculty WHERE verify = :verify AND name LIKE :search");
			query2.setParameter("search", "%" + searchQuery + "%");
			query2.setParameter("verify", "Approve");
			students = query2.list();
		} else {
			Query query1 = session.createQuery("FROM faculty WHERE verify = :verify");
			query1.setParameter("verify", "Approve");
			students = query1.list();
		}
		m.addAttribute("faculty_list", students);
		return "/views/admin/ajax/facultytable.jsp";
	}

}
