package com.student.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.student.model.Student;
import com.student.config.DB;

public class StudentDB {

	public ArrayList<Student> select() {
		ArrayList<Student> students = new ArrayList<Student>();

		try {
			Statement statement = getDBConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String middleName = resultSet.getString(3);
				String lastName = resultSet.getString(4);
				Student student = new Student(id, firstName, middleName, lastName);

				students.add(student);
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return students;
	}

	public Student selectOne(int id) {
		String sql = "SELECT * FROM students WHERE id=?";

		try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int studentId = resultSet.getInt(1);
				String firstName = resultSet.getString(2);
				String middleName = resultSet.getString(3);
				String lastName = resultSet.getString(4);

				return new Student(studentId, firstName, middleName, lastName);
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return null;
	}

	public Student insert(Student student) {
		String sql = "INSERT INTO students (first_name, middle_name, last_name) Values (?, ?, ?)";

		try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getMiddleName());
			preparedStatement.setString(3, student.getLastName());

			if (preparedStatement.executeUpdate() != 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();

				if (resultSet.next())
					return selectOne(resultSet.getInt(1));
			}
		}

		catch (SQLException ex) {
			System.out.println(ex);
		}

		return null;
	}

	public Student update(Student student) {
		String sql = "UPDATE students SET first_name = ?, middle_name = ?, last_name = ? WHERE id = ?";

		try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(sql)) {
			preparedStatement.setString(1, student.getFirstName());
			preparedStatement.setString(2, student.getMiddleName());
			preparedStatement.setString(3, student.getLastName());
			preparedStatement.setInt(4, student.getId());

			if (preparedStatement.executeUpdate() != 0)
				return selectOne(student.getId());
		}

		catch (SQLException ex) {
			System.out.println(ex);
		}

		return null;
	}

	public int delete(int id) {		
		String sql = "DELETE FROM students WHERE id = ?";
		
		try (PreparedStatement preparedStatement = getDBConnection().prepareStatement(sql)) {
			preparedStatement.setInt(1, id);

			return preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		}

		return 0;
	}

	public Connection getDBConnection() {
		try {
			Class.forName(DB.getNamedriver()).getDeclaredConstructor().newInstance();

			return DriverManager.getConnection(DB.getUrl(), DB.getUsername(), DB.getPassword());
		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return null;
	}
}
