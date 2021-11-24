package com.student.example;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.model.Student;
import com.student.pojo.CreateStudentPojo;
import com.student.pojo.UpdateStudentPojo;
import com.student.service.StudentDB;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ObjectMapper mapper;
	private StudentDB studentDB;

	public StudentServlet() {
		super();

		this.mapper = new ObjectMapper();
		this.studentDB = new StudentDB();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		if (id != null) {

			String output = this.mapper.writeValueAsString(studentDB.selectOne(Integer.parseInt(id)));
			response.getWriter().append(output);

			return;
		}

		String output = this.mapper.writeValueAsString(studentDB.select());
		response.getWriter().append(output);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		CreateStudentPojo input = this.mapper.readValue(body, CreateStudentPojo.class);

		Student student = new Student(input.getFirstName(), input.getMiddleName(), input.getLastName());

		String output = this.mapper.writeValueAsString(studentDB.insert(student));
		response.getWriter().append(output);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		UpdateStudentPojo input = this.mapper.readValue(body, UpdateStudentPojo.class);

		Student student = new Student(Integer.parseInt(id), input.getFirstName(), input.getMiddleName(),
				input.getLastName());

		String output = this.mapper.writeValueAsString(studentDB.update(student));
		response.getWriter().append(output);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		studentDB.delete(Integer.parseInt(id));
	}
}
