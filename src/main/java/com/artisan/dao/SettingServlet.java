package com.artisan.dao;

import com.artisan.util.DbUtil;
import com.artisan.util.StringParse;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingServlet {
	@WebServlet(name = "Update", value = "/setting")
	public static class Update extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "0");
			response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,Authorization,SessionToken,JSESSIONID,token");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			String key = StringParse.parse(request.getParameter("key"));
			String value = StringParse.parse(request.getParameter("value"));
			String sql = String.format("update setting set value='%s' where name='%s'", value, key);
			System.out.println(sql);
			Connection connection = DbUtil.getConnection();
			HashMap<String, String> hashMap = new HashMap<>();
			try {
				hashMap.put("status", "success");
				DbUtil.execute(connection, sql);
			} catch (Exception e) {
				hashMap.put("status", "fail");
				e.printStackTrace();
			}
			response.getWriter().print(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "ShowSetting", value = "/setting/show")
	public static class Show extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			String sql = "select * from setting";
			Connection connection = DbUtil.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			ArrayList<Object> list = new ArrayList<>();
			HashMap<String, Object> hashMap = new HashMap<>();
			while (resultSet.next()) {
				hashMap.put("name", resultSet.getString(1));
				hashMap.put("value", resultSet.getObject(2));
				list.add(hashMap);
			}
			response.getWriter().print(JSONValue.toJSONString(list));
		}
	}

	@WebServlet(name = "CheckSetting", value = "/setting/check")
	public static class Check extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			String name = request.getParameter("name");
			String sql = String.format("select * from setting where name = '%s'", name);
			Connection connection = DbUtil.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				response.getWriter().print(resultSet.getString(2));
			}
		}
	}
}
