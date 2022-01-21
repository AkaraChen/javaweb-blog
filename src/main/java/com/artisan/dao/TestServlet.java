package com.artisan.dao;

import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(name = "TestServlet", value = "/test")
public class TestServlet extends HttpServlet {
	@SneakyThrows
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = response.getWriter();
		out.println(request.getParameter("id"));
	}
}
