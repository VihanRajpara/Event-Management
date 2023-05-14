package com.crud.databse;

import java.sql.*;

public class DBConnection {
	private static DBConnection instance;
	private Connection conn;

	private DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/event", "root", "");
			System.out.println("-----------------------------------------Database connection successful!------------------------------------");
		} catch (ClassNotFoundException | SQLException e) {
		}
	}

	public static synchronized DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		return conn;
	}
}
