package com.crud.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.faculty;
import com.crud.dao.hod;
import com.crud.dao.event;
import com.crud.dao.student;
import com.crud.function.QRCodeGenerator;
import com.crud.dao.register;
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
			req.getSession().setAttribute("rtype", stu_obj.getType());
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
	
	//--------------------------------------------------------------Event-----------------------------------------------------------------------------
	
	@RequestMapping(value = "/student/register", method = RequestMethod.GET)
	public String Newevent(Model m, HttpServletRequest req, HttpServletResponse res, @RequestParam("id") int id, @RequestParam("email") String email) throws IOException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM student WHERE email = :email");
		quer.setParameter("email", email);
		student stu_obj = (student) quer.getSingleResult();
		Query query = session.createQuery("FROM event WHERE id = :id");
		query.setParameter("id", id);
		event eve_obj = (event) query.getSingleResult();
		m.addAttribute("event", eve_obj);
		m.addAttribute("student", stu_obj);
		return "/views/student/registerform.jsp";
	}
	
	@RequestMapping(value = "/student/newevent", method = RequestMethod.GET)
	public String eventRegister(Model m, HttpServletRequest req, HttpServletResponse res) throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM student WHERE email = :email");
		quer.setParameter("email", email);
		student stu_obj = (student) quer.getSingleResult();
		Query query = session.createQuery("FROM event WHERE permission =:per AND type = :type");
		query.setParameter("per","done");
		query.setParameter("type",stu_obj.getDep());
		List event = query.list();
		query.setParameter("type","All");
		List eventss = query.list();
		
		Query query3 = session.createQuery("FROM register WHERE student_id =:id");
		query3.setParameter("id",stu_obj.getId());
		List registerevent = query3.list();
		
		session.close();
		m.addAttribute("events", event);
		m.addAttribute("eventsss", eventss);
		m.addAttribute("registerevent", registerevent);
		return "/views/student/newevent.jsp";
	}
	
	@RequestMapping(value = "/student/registeraction", method = RequestMethod.POST)
	public void eventRegisteraction(Model m, HttpServletRequest req, HttpServletResponse res,@RequestParam("event") int eid,@RequestParam("student") int sid) throws IOException, SQLException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		Query quer = session.createQuery("FROM student WHERE id = :id");
		quer.setParameter("id", sid);
		student stu_obj = (student) quer.getSingleResult();
		Query query = session.createQuery("FROM event WHERE id = :id");
		query.setParameter("id", eid);
		event eve_obj = (event) query.getSingleResult();
		if(eve_obj.getStatus().equals("On")) {
			register register=new register();
			register.setEvent(eve_obj);
			register.setStudent(stu_obj);
			register.setFee("Unpaid");
			String qrCodeContent = "Name: " + register.getStudent().getName() + "\nEmail: "
					+ register.getStudent().getEmail() + "\nID: " + register.getStudent().getId() + "\nSemester: "
					+ register.getStudent().getSem() + "\nFaculty Name: " + register.getStudent().getFaculty().getName()
					+ "\nHOD Name: " + register.getStudent().getHod().getName();

			int qrCodeWidth = 300;
			int qrCodeHeight = 300;
			
			//Genrate QR CODE
			byte[] qrCodeImageBytes = QRCodeGenerator.generateQRCode(qrCodeContent, qrCodeWidth, qrCodeHeight);
			
			register.setQr(qrCodeImageBytes);
			Transaction t = session.beginTransaction();
			session.save(register);
			t.commit();
		}
		session.close();
		res.sendRedirect("newevent");
	}
	
	@RequestMapping(value = "/student/removeregister", method = RequestMethod.GET)
	public void removeRegisteraction(Model m, HttpServletRequest req, HttpServletResponse res,@RequestParam("id") int id) throws IOException, SQLException {
		Session session = HibernetConnection.getSessionFactory().openSession();
		register register=new register();
		register.setId(id);
		Transaction t = session.beginTransaction();
		session.delete((Object)register);
		t.commit();
		session.close();
		res.sendRedirect("newevent");
	}
}
