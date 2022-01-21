package com.artisan.dao;

import com.artisan.util.DbUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryGet {
	// 根据分类 ID 获取分类名称
	@SneakyThrows
	public static String byId(Integer id) {
		Connection connection = DbUtil.getConnection();
		String sql = String.format("select name from category where id = %s", id);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		if (!resultSet.next()) {
			statement.close();
			connection.close();
			return null;
		} else {
			String string = resultSet.getString(1);
			statement.close();
			connection.close();
			return string;
		}
	}
}
