package com.artisan.model;

import lombok.Data;

import java.sql.SQLException;

@Data
public class Post {
	private String content;
	private Integer author;

	public void setAuthor(Integer author) throws SQLException, ClassNotFoundException {
		if (User.checkExist(author)) {
			this.author = author;
		}
	}

	public Post(String content, Integer author) throws SQLException, ClassNotFoundException {
		setContent(content);
		setAuthor(author);
	}
}
