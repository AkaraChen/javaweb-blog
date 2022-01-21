package com.artisan.util;

import java.sql.*;

public class DbUtil {
	static String user = "root";
	static String password = "7xtUoydjBXDI85v6";

	// 获取 connection
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/xuexi?useUnicode=true&characterEncoding=utf-8", user, password);
	}

	// 执行 sql，不返回结果集（用于 select 外的命令）
	public static void execute(Connection connection, String sql) throws SQLException, ClassNotFoundException {
		Statement statement = connection.createStatement();
		statement.execute(sql);
		statement.close();
	}
}
