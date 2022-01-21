package com.artisan.dao;

import com.artisan.model.User;
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

public class UserServlet {
	@WebServlet(name = "UserAdd", value = "/user/add")
	public static class Add extends HttpServlet {
		// 传入用户名 name，密码 password，安全密钥 key，返回状态信息 status，用户名 name
		@Override
		@SneakyThrows
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			HashMap<String, String> hashMap = new HashMap<>();
			PrintWriter out = response.getWriter();
			String name = StringParse.parse(request.getParameter("name"));
			String password = StringParse.parse(request.getParameter("password"));
			String key = StringParse.parse(request.getParameter("key"));
			if (Verify.isAdmin(key)) {
				User user = new User(name, password);
				try {
					Connection connection = DbUtil.getConnection();
					String sql = String.format("insert into xuexi.user (name,passwd) values ('%s','%s')",
							user.getName(), user.getPassword());
					DbUtil.execute(connection, sql);
					hashMap.put("status", "success");
					hashMap.put("name", user.getName());
					connection.close();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					hashMap.put("status", "fail");
				}
			} else {
				hashMap.put("status", "unAuthed");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "changePassed", value = "/user/password/change")
	public static class ChangePasswd extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			String id = StringParse.parse(request.getParameter("id"));
			String passwd = StringParse.parse(request.getParameter("passwd"));
			String sql = String.format("update xuexi.user set passwd = '%s' where id = %s", passwd, id);
			HashMap<String, String> hashMap = new HashMap<>();
			PrintWriter out = response.getWriter();
			try {
				Connection connection = DbUtil.getConnection();
				Statement statement = connection.createStatement();
				statement.execute(sql);
				statement.close();
				connection.close();
				hashMap.put("status", "success");
			} catch (Exception e) {
				e.printStackTrace();
				hashMap.put("status", "fail");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "AllUser", value = "/user/all")
	public static class AllUser extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			ArrayList<Object> arrayList = new ArrayList<>();
			Connection connection = DbUtil.getConnection();
			String sql = "select id,name from user";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", resultSet.getInt(1));
				hashMap.put("name", resultSet.getString(2));
				arrayList.add(hashMap);
			}
			statement.close();
			connection.close();
			out.println(JSONValue.toJSONString(arrayList));
		}
	}

	@WebServlet(name = "UserInfo", value = "/userinfo")
	public static class UserInfo extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			Integer id = Integer.valueOf(StringParse.parse(request.getParameter("id")));
			HashMap<String, Object> hashMap = new HashMap<>();
			Connection connection = DbUtil.getConnection();
			Statement statement = connection.createStatement();
			String sql = String.format("select id,name from user where id = %s", id);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				hashMap.put("id", id);
				hashMap.put("name", resultSet.getString(2));
			}
			response.getWriter().print(JSONObject.toJSONString(hashMap));
		}
	}
}
