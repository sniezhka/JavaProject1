package com.student.config;

public class DB {
	private static final String url = "jdbc:mysql://localhost/studentsDB?serverTimezone=Europe/Kiev&useSSL=false";
    private static final String username = "app";
    private static final String password = "secretpass";
    private static final String nameDriver = "com.mysql.cj.jdbc.Driver";
	
    public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static String getNamedriver() {
		return nameDriver;
	}
}