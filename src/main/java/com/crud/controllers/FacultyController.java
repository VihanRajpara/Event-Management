package com.crud.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crud.dao.event;
import com.crud.dao.register;
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
		hod hod_obj = session.get(hod.class, hodId);
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
		String cemail = faculty.getEmail();
		String cpasswprd = faculty.getPass();
		if (faculty != null) {
			if (email.equals(cemail) && password.equals(cpasswprd)) {
				req.getSession().setAttribute("email", email);
				req.getSession().setAttribute("type", type);
				req.getSession().setAttribute("verify", faculty.getVerify());
				res.sendRedirect("faculty/dashboard");
			} else {
				res.sendRedirect("login");
			}
		} else {
			res.sendRedirect("login");
		}

	}
	
	//----------------------------------------------Student-------------------------------------------------------------------
	
	@RequestMapping(value = "/faculty/approvalstudent", method = RequestMethod.GET)
	public String Approvalpage(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
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

	@RequestMapping(value = "/faculty/allstudent", method = RequestMethod.GET)
	public ModelAndView AllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sem", required = false) Integer sem) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		System.out.println("     ============    " + sem);
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM faculty WHERE email = :email");
		query.setParameter("email", email);
		faculty fac_obj = (faculty) query.getSingleResult();
		List students;
		List crs;
		Query query1 = session
				.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify");
		Query query2 = session
				.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify AND type = :type");
		query1.setParameter("facid", fac_obj.getId());
		query1.setParameter("verify", "Approve");
		query2.setParameter("facid", fac_obj.getId());
		query2.setParameter("verify", "Approve");
		students = query1.list();
		query2.setParameter("type", "CR");
		crs = query2.list();
		session.close();
		ModelAndView mav = new ModelAndView("/views/faculty/allstudent.jsp");
		mav.addObject("student_list", (Object) students);
		mav.addObject("student_cr", (Object) crs);
		return mav;
	}
	
	@RequestMapping(value = "/faculty/studentaccess")
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
		res.sendRedirect("approvalstudent");
	}

	@RequestMapping(value = "/faculty/studentcr")
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
	
	//---------------------------------------------------------------------------Event-----------------------------------------------------------------------

	
	@RequestMapping(value = "/faculty/newevent", method = RequestMethod.GET)
	public String Newevent(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM faculty WHERE email = :email");
		quer.setParameter("email", email);
		faculty fac_obj = (faculty) quer.getSingleResult();
		Query query = session.createQuery("FROM event WHERE permission =:per AND type = :type");
		query.setParameter("per","done");
		query.setParameter("type",fac_obj.getDep());
		List event = query.list();
		query.setParameter("type","All");
		List eventss = query.list();
		Query query1 = session.createQuery("FROM event WHERE permission =:per AND type =:type");
		query1.setParameter("per","Under Approval");
		query1.setParameter("type",fac_obj.getDep());
		List events = query1.list();
		session.close();
		m.addAttribute("events", event);
		m.addAttribute("newevent",events);
		m.addAttribute("eventsss", eventss);
		return "/views/faculty/newevent.jsp";
	}
	
	@RequestMapping(value = "/faculty/addevent", method = RequestMethod.POST)
	public void Addeventaction(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam("name") String name,
			@RequestParam("info") String info,
			@RequestParam("contact") String contact,
			@RequestParam("fee") String fee)
			throws IOException, ServletException {
		String email = (String) req.getSession().getAttribute("email");
		String mtype = (String) req.getSession().getAttribute("type");
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM faculty WHERE email = :email");
		quer.setParameter("email", email);
		faculty hod_obj = (faculty) quer.getSingleResult();
		event event=new event();
		event.setContact(contact);
		event.setEmail(email);
		event.setInfo(info);
		event.setMtype(mtype);
		event.setName(name);
		event.setPermission("Under Approval");
		event.setStatus("On");
		event.setType(hod_obj.getDep());
		event.setFee(fee);
		Transaction t = session.beginTransaction();
		t.commit();
		session.save(event);
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/faculty/eventstatus")
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
	
	@RequestMapping(value = "/faculty/eventview")
	public String Eventview(Model m,HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM register WHERE event_id = :id");
		query2.setParameter("id",id);
		List events = query2.list();
		m.addAttribute("events", events);
		m.addAttribute("eid",id);
		return "/views/faculty/viewregister.jsp";
	}
	
	@RequestMapping(value = "/faculty/eventfeestatus")
	public void Eventfeestatus(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id,@RequestParam("eid") int eid)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM register WHERE id = :id");
		query2.setParameter("id", (Object) id);
		register reg_obj = (register) query2.getSingleResult();
		if(reg_obj.getFee().equals("Unpaid")) {
			reg_obj.setFee("Paid");
		}else {
			reg_obj.setFee("Unpaid");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) reg_obj);
		t.commit();
		session.close();
		res.sendRedirect("eventview?id="+eid);
	}
	
	//------------------------------------------------------AJAX---------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/faculty/newallstudent", method = RequestMethod.GET)
	public String  NewAllStudent(Model m, HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "sem", required = false) Integer sem,
			@RequestParam(value = "search", required = false) String searchQuery) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		System.out.println("     ============    " + sem);
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query = session.createQuery("FROM faculty WHERE email = :email");
		query.setParameter("email", email);
		faculty fac_obj = (faculty) query.getSingleResult();
		List students;
		if(sem != null && searchQuery != null && !searchQuery.isEmpty()) {
			Query query2 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify AND sem = :sem AND name LIKE :search");
	        query2.setParameter("search", "%" + searchQuery + "%");
	        query2.setParameter("facid", fac_obj.getId());
	        query2.setParameter("sem", sem);
			query2.setParameter("verify", "Approve");
	        students = query2.list();
		}
		else if (searchQuery != null && !searchQuery.isEmpty()) {
			Query query2 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify AND name LIKE :search");
	        query2.setParameter("search", "%" + searchQuery + "%");
	        query2.setParameter("facid", fac_obj.getId());
			query2.setParameter("verify", "Approve");
	        students = query2.list();
	    }
		else if(sem != null) {
			Query query1 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify AND sem = :sem");
			query1.setParameter("facid", fac_obj.getId());
			query1.setParameter("verify", "Approve");
			query1.setParameter("sem", sem);
			students = query1.list();
		}else {
			Query query1 = session.createQuery("FROM student WHERE faculty_id = :facid AND verify = :verify");
			query1.setParameter("facid", fac_obj.getId());
			query1.setParameter("verify", "Approve");
			students = query1.list();
		}
		 m.addAttribute("student_list", students);
		 return "/views/faculty/ajax/newstudenttable.jsp";
	}
	
}


