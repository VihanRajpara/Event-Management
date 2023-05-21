package com.crud.controllers;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.crud.function.*;
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
	
	//-------------------------------------------Faculty-----------------------------------------------------------------------------

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

	//------------------------------------------------------------------student--------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------EVENT-------------------------------------------------------------------------------
	
	@RequestMapping(value = "/hod/newevent", method = RequestMethod.GET)
	public String Newevent(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM hod WHERE email = :email");
		quer.setParameter("email", email);
		hod hod_obj = (hod) quer.getSingleResult();
		Query query = session.createQuery("FROM event WHERE permission =:per AND type = :type");
		query.setParameter("per","done");
		query.setParameter("type",hod_obj.getDep());
		List event = query.list();
		query.setParameter("type","All");
		List eventss = query.list();
		Query query1 = session.createQuery("FROM event WHERE permission =:per AND type =:type");
		query1.setParameter("per","Under Approval");
		query1.setParameter("type",hod_obj.getDep());
		List events = query1.list();
		session.close();
		m.addAttribute("events", event);
		m.addAttribute("eventsss", eventss);
		m.addAttribute("newevents", events);
		return "/views/hod/newevent.jsp";
	}
	
	@RequestMapping(value = "/hod/addevent", method = RequestMethod.POST)
	public void Addeventaction(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam("name") String name,
			@RequestParam("info") String info,
			@RequestParam("contact") String contact,
			@RequestParam("fee") String fee)
			throws IOException, ServletException {
		String email = (String) req.getSession().getAttribute("email");
		String mtype = (String) req.getSession().getAttribute("type");
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM hod WHERE email = :email");
		quer.setParameter("email", email);
		hod hod_obj = (hod) quer.getSingleResult();
		event event=new event();
		event.setContact(contact);
		event.setEmail(email);
		event.setInfo(info);
		event.setMtype(mtype);
		event.setName(name);
		event.setPermission("done");
		event.setStatus("On");
		event.setType(hod_obj.getDep());
		event.setFee(fee);
		Transaction t = session.beginTransaction();
		t.commit();
		session.save(event);
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/hod/eventstatus")
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
	
	@RequestMapping(value = "/hod/eventaccess")
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
	
	@RequestMapping(value = "/hod/eventdelete" )
	public void Eventdelete(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		event event=new event();
		event.setId(id);
		Transaction t = session.beginTransaction();
		session.delete((Object) event);
		t.commit();
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/hod/eventview")
	public String Eventview(Model m,HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM register WHERE event_id = :id");
		query2.setParameter("id",id);
		List events = query2.list();
		m.addAttribute("events", events);
		m.addAttribute("eid",id);
		return "/views/hod/viewregister.jsp";
	}
	
	@RequestMapping(value = "/hod/eventfeestatus")
	public void Eventfeestatus(HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id,@RequestParam("eid") int eid)
			throws IOException, ServletException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM register WHERE id = :id");
		query2.setParameter("id", (Object) id);
		register reg_obj = (register) query2.getSingleResult();
		if(reg_obj.getFee().equals("Unpaid")) {
			reg_obj.setFee("Paid");
			byte[] qrCodeImageBytes=reg_obj.getQr();
			String recipientEmail = reg_obj.getStudent().getEmail();
			String subject = "Payment Confirmation";
			String body = "Dear " + reg_obj.getStudent().getName() + ",\n\nYour payment has been confirmed. "
					+ "Please find attached the QR Code for your registration.\n\nThank you.";
			EmailSender.sendEmailWithAttachment(recipientEmail, subject, body, qrCodeImageBytes);
		}else {
			reg_obj.setFee("Unpaid");
		}
		Transaction t = session.beginTransaction();
		session.saveOrUpdate((Object) reg_obj);
		t.commit();
		session.close();
		res.sendRedirect("eventview?id="+eid);
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
