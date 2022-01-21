package com.artisan.dao;

import com.artisan.util.DbUtil;
import com.artisan.util.StringParse;
import com.artisan.util.Verify;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryServlet {

	// 必传：分类名 name，安全密钥 key，返回状态信息 status 和分类名 name
	@WebServlet(name = "CategoryAdd", value = "/category/add")
	public static class Add extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			HashMap<String, String> hashMap = new HashMap<>();
			PrintWriter out = response.getWriter();
			String name = StringParse.parse(request.getParameter("name"));
			String key = StringParse.parse(request.getParameter("key"));
			if (Verify.isAdmin(key)) {
				try {
					Connection connection = DbUtil.getConnection();
					String sql = String.format("insert into xuexi.category (name) value ('%s')", name);
					DbUtil.execute(connection, sql);
					hashMap.put("status", "success");
					hashMap.put("name", name);
					connection.close();
				} catch (ClassNotFoundException | SQLException e) {
					hashMap.put("status", "fail");
					e.printStackTrace();
				}
			} else {
				hashMap.put("status", "UnAuthed");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "All", value = "/category/all")
	public static class All extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			ArrayList<Object> arrayList = new ArrayList<>();
			Connection connection = DbUtil.getConnection();
			String sql = "select id,name from category";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", resultSet.getInt(1));
				hashMap.put("name", resultSet.getString(2));
				arrayList.add(hashMap);
			}
			out.println(JSONValue.toJSONString(arrayList));
		}
	}

	@WebServlet(name = "Count", value = "/category/count")
	public static class Count extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			String sql;
			Integer category = Integer.valueOf(request.getParameter("category"));
			if (request.getParameter("category") != null) {
				sql = String.format("select * from post where category = %s", category);
			} else {
				sql = "select * from post";
			}
			PrintWriter out = response.getWriter();
			HashMap<String, Integer> hashMap = new HashMap<>();
			Connection connection = DbUtil.getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.last();
			hashMap.put("count", resultSet.getRow());
			out.println(JSONValue.toJSONString(hashMap));
		}
	}
}

