package com.student.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDB {

	private static String url = "jdbc:mysql://localhost/studentsDB?serverTimezone=Europe/Kiev&useSSL=false";
    private static String username = "app";
    private static String password = "secretpass";
    
    public static ArrayList<Student> select() {
         
        ArrayList<Student> students = new ArrayList<Student>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
                while(resultSet.next()){
                      
                    int id = resultSet.getInt(1);
                    String first_name = resultSet.getString(2);
                    String middle_name = resultSet.getString(3);
                    String last_name = resultSet.getString(4);
                    Student student = new Student(id, first_name, middle_name, last_name);
                    students.add(student);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return students;
    }
    public static Student selectOne(int id) {
         
        Student student = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                String sql = "SELECT * FROM students WHERE id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
 
                    	int studentId = resultSet.getInt(1);
                        String first_name = resultSet.getString(2);
                        String middle_name = resultSet.getString(3);
                        String last_name = resultSet.getString(4);
                        student = new Student(studentId, first_name, middle_name, last_name);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return student;
    }
    public static int insert(Student student) {
         
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                String sql = "INSERT INTO students (first_name, middle_name, last_name) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, student.getFirstName());
                    preparedStatement.setString(2, student.getMiddleName());
                    preparedStatement.setString(3, student.getLastName());
                   
                      
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
     
    public static int update(Student student) {
         
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                String sql = "UPDATE students SET first_name = ?, middle_name = ?, last_name = ? WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, student.getFirstName());
                    preparedStatement.setString(2, student.getMiddleName());
                    preparedStatement.setString(3, student.getLastName());
                    preparedStatement.setInt(4, student.getId());
                      
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public static int delete(int id) {
         
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                  
                String sql = "DELETE FROM students WHERE id = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                      
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
}
