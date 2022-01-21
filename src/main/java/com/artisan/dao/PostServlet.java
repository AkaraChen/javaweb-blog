package com.artisan.dao;

import com.artisan.model.Post;
import com.artisan.util.DateUtil;
import com.artisan.util.DbUtil;
import com.artisan.util.StringParse;
import com.artisan.util.e;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class PostServlet {
	@WebServlet(name = "GetPost", value = "/post/get")
	public static class PostGetServlet extends HttpServlet {
		// 必传文章编号 id，返回文章内容 content，作者 author，文章创建日期 time，文章浏览次数 visit，状态信息 status
		// 文章标题 title
		@Override
		@SneakyThrows
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			HashMap<String, Object> hashMap = new HashMap<>();
			Integer id = Integer.valueOf(StringParse.parse(request.getParameter("id")));
			String sql = String.format("select content,author,time,visit,category,title from post where id = %s", id);
			try {
				Connection connection = DbUtil.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				if (!resultSet.next()) {
					hashMap.put("status", "NotFound");
				} else {
					String update = String.format("update post set visit = visit + 1 where id = %s", id);
					DbUtil.execute(connection, update);
					hashMap.put("content", StringParse.parse(resultSet.getString(1)));
					hashMap.put("author", UserGet.byPost(id));
					hashMap.put("time", resultSet.getString(3));
					hashMap.put("visit", resultSet.getString(4));
					hashMap.put("category", CategoryGet.byId(resultSet.getInt(5)));
					hashMap.put("status", "success");
					hashMap.put("title", resultSet.getString(6));
					hashMap.put("id", id);
				}
				statement.close();
				connection.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				hashMap.put("status", "fail");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "PostAdd", value = "/post/add")
	public static class PostAddServlet extends HttpServlet {
		// 必传：文章内容 content，文章作者数字编号 id，安全密钥 key，返回状态信息 status 和作者名 author 及日期 date
		@Override
		@SneakyThrows
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			request.setCharacterEncoding("utf-8");
			HashMap<String, String> hashMap = new HashMap<>();
			PrintWriter out = response.getWriter();
			String content = StringParse.parse(request.getParameter("content"));
			Integer author = Integer.valueOf(StringParse.parse(request.getParameter("author")));
			String key = StringParse.parse(request.getParameter("key"));
			Integer category = Integer.valueOf(StringParse.parse(request.getParameter("category")));
			String title = StringParse.parse(request.getParameter("title"));
			if (key.equals(author.toString())) {
				try {
					Post post = new Post(content, author);
					if (post.getAuthor() != null) {
						Connection connection = DbUtil.getConnection();
						Statement statement = connection.createStatement();
						String sql = String.format("insert into xuexi.post (content, author, time, title, category) value ('%s',%s,'%s','%s', %s)", post.getContent(), post.getAuthor(), DateUtil.encode(new Date()), title, category);
						statement.execute(sql);
						hashMap.put("status", "success");
						hashMap.put("author", post.getAuthor().toString());
						hashMap.put("date", DateUtil.encode(new Date()));
						statement.close();
						connection.close();
					} else {
						hashMap.put("status", "AuthorNotFound");
					}
				} catch (ClassNotFoundException | SQLException e) {
					hashMap.put("status", "fail");
					e.printStackTrace();
				}
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "RandomPost", value = "/post/random")
	public static class RandomPostServlet extends HttpServlet {
		@SneakyThrows
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			LinkedList<Object> list = new LinkedList<>();
			PrintWriter out = response.getWriter();
			Connection connection = DbUtil.getConnection();
			String sql = "select id,title from xuexi.post order by rand() limit 4";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", resultSet.getObject(1));
				hashMap.put("title", resultSet.getObject(2));
				list.add(hashMap);
			}
			statement.close();
			connection.close();
			out.println(JSONValue.toJSONString(list));
		}
	}

	@WebServlet(name = "Postlist", value = "/post/list")
	public static class Postlist extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doGet(HttpServletRequest request, HttpServletResponse response) {
			int pid = request.getParameter("id") != null ? e.toInt(request.getParameter("id")) - 1 : 0;
			int size = request.getParameter("size") != null ? e.toInt(request.getParameter("size")) : 3;
			LinkedList<Object> list = new LinkedList<>();
			PrintWriter out = response.getWriter();
			Connection connection = DbUtil.getConnection();
			Statement statement = connection.createStatement();
			String sql = String.format("select id,title,content from post order by id desc limit %s offset %s;", size, pid * size);
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("id", resultSet.getInt(1));
				hashMap.put("title", resultSet.getString(2));
				hashMap.put("content", resultSet.getString(3));
				list.add(hashMap);
			}
			out.println(JSONValue.toJSONString(list));
		}
	}

	@WebServlet(name = "PostDel", value = "/post/del")
	public static class PostDel extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			Integer id = Integer.valueOf(request.getParameter("id"));
			PrintWriter out = response.getWriter();
			Connection connection = DbUtil.getConnection();
			String sql = String.format("delete from post where id = %s", id);
			HashMap<String, String> hashMap = new HashMap<>();
			try {
				DbUtil.execute(connection, sql);
				hashMap.put("status", "success");
			} catch (Exception e) {
				e.printStackTrace();
				hashMap.put("status", "failed");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}

	@WebServlet(name = "PostUpdate", value = "/post/update")
	public static class PostUpdate extends HttpServlet {
		@Override
		@SneakyThrows
		protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			Integer id = Integer.valueOf(request.getParameter("id"));
			String post = StringParse.parse(request.getParameter("content"));
			PrintWriter out = response.getWriter();
			Connection connection = DbUtil.getConnection();
			HashMap<String, String> hashMap = new HashMap<>();
			String sql = String.format("update post set content = '%s' where id = %s", post, id);
			try {
				DbUtil.execute(connection, sql);
				hashMap.put("status", "success");
			} catch (Exception e) {
				e.printStackTrace();
				hashMap.put("status", "faled");
			}
			out.println(JSONObject.toJSONString(hashMap));
		}
	}
}
