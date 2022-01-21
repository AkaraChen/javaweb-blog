package com.artisan.dao;

import com.artisan.util.DbUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserGet {
	// 根据 ID 获取用户名
	public static String byID(Integer id) throws SQLException, ClassNotFoundException {
		Connection connection = DbUtil.getConnection();
		String sql = String.format("select * from user where id = %s", id);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if (!resultSet.next()) {
			connection.close();
			return null;
		} else {
			String string = resultSet.getString(2);
			connection.close();
			return string;
		}

	}

	@SneakyThrows
	public static String byPost(Integer pid) {
		Connection connection = DbUtil.getConnection();
		String sql = String.format("select author from post where id = %s", pid);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if (!resultSet.next()) {
			statement.close();
			connection.close();
			return null;
		} else {
			Integer num = resultSet.getInt(1);
			statement.close();
			connection.close();
			return UserGet.byID(num);
		}
	}
}
