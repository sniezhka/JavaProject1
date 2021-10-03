package com.student.example;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.example.model.Student;
import com.student.example.model.StudentDB;
import com.student.pojo.CreateStudentPojo;
import com.student.pojo.UpdateStudentPojo;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ObjectMapper mapper;
    

    public StudentServlet() {
        super();
        
        this.mapper = new ObjectMapper();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		if(id != null) {
		
			String output = this.mapper.writeValueAsString(StudentDB.selectOne(Integer.parseInt(id)));
	
			response.getWriter().append(output);
			
			return;
		}
		
		String output = this.mapper.writeValueAsString(StudentDB.select());
			
		response.getWriter().append(output);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		CreateStudentPojo input = this.mapper.readValue(body, CreateStudentPojo.class);
		
		StudentDB.insert(new Student(
			input.getFirstName(),
			input.getMiddleName(),
			input.getLastName()
		));
		
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		UpdateStudentPojo input = this.mapper.readValue(body, UpdateStudentPojo.class);
		
		StudentDB.update(new Student(
				Integer.parseInt(id),
				input.getFirstName(),
				input.getMiddleName(),
				input.getLastName()
			));
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if(id == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		StudentDB.delete(Integer.parseInt(id));
		
	}

}
