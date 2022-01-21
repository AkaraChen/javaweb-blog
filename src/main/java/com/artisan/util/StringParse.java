package com.artisan.util;

import java.nio.charset.StandardCharsets;

public class StringParse {
	// 万能其他编码转 UTF-8
	public static String parse(String str) {
		return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
	}
}
