package com.artisan.dao;

import com.artisan.util.DbUtil;
import com.artisan.util.StringParse;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;

public class SettingServlet {
	@WebServlet(name = "Update", value = "/setting")
	public static class Update extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
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
}
