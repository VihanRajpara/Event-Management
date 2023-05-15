package com.crud.controllers;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.dao.hod;
import com.crud.hibernet.HibernetConnection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WelcomeController {
	public WelcomeController() {
		System.out.println("------------------------------Welcome Controller------------------------------");
	}

	@RequestMapping(value = "/login")
	public String Login() {
		return "/views/login.jsp";
	}
	
	@RequestMapping(value = "/signup")
	public String Signup(Model m) {
		Session session = null;
		session = HibernetConnection.getSessionFactory().openSession();
		Query query2 = session.createQuery("FROM hod");
		Query query = session.createQuery("FROM dep");
		Query query1 = session.createQuery("FROM faculty WHERE verify = :verify");
		query1.setParameter("verify", "Approve");
		List hods = query2.list();
		List deps = query.list();
		List facs = query1.list();
		session.close();
		m.addAttribute("hod_list", (Object) hods);
		m.addAttribute("dep_list", (Object) deps);
		m.addAttribute("fac_list", (Object) facs);
		return "/views/signup.jsp";
	}
	
	@RequestMapping(value = "/signupcheck", method = RequestMethod.POST)
	public void Signupcheck(@RequestParam("type") String type,HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (type.equals("faculty")) {
			RequestDispatcher rd = req.getRequestDispatcher("faculty/signupaction"); 
			rd.forward(req, res);
		} else if (type.equals("student")) {
			RequestDispatcher rd = req.getRequestDispatcher("student/signupaction");
			rd.forward(req, res);
		}	
	}

	@RequestMapping(value = "/usercheck", method = RequestMethod.POST)
	public void Userlogin(@RequestParam("type") String type, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		if (type.equals("admin")) {
			RequestDispatcher rd = req.getRequestDispatcher("admin/loginaction");
			rd.forward(req, res);
		} else if (type.equals("hod")) {
			RequestDispatcher rd = req.getRequestDispatcher("hod/loginaction");
			rd.forward(req, res);
		} else if (type.equals("faculty")) {
			RequestDispatcher rd = req.getRequestDispatcher("faculty/loginaction");
			rd.forward(req, res);
		} else if (type.equals("student")) {
			RequestDispatcher rd = req.getRequestDispatcher("student/loginaction");
			rd.forward(req, res);
		}
	}
}
