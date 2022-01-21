package com.artisan.model;

import com.artisan.util.DbUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class User {
	private String name;
	private String password;

	public void setName(String name) {
		this.name = checkName(name) ? name : "不合法昵称";
	}

	public void setPassword(String password) {
		this.password = checkPwd(password) ? password : "123456";
	}

	public User(String name, String password) {
		setName(name);
		setPassword(password);
	}

	public static boolean checkName(String name) {
		String regExp = "^[\\w_]{5,9}$";
		return name.matches(regExp);
	}

	public static boolean checkPwd(String pwd) {
		String regExp = "^[\\w_]{6,20}$";
		return pwd.matches(regExp);
	}

	public static boolean checkExist(Integer id) throws SQLException, ClassNotFoundException {
		Connection connection = DbUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(String.format("select * from user where id = %s", id));
		return resultSet.next();
	}
}
