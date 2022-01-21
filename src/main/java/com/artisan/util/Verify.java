package com.artisan.util;

import java.util.Objects;

public class Verify {

	// 暂时没能力做，所以就写了个简单的判断
	public static boolean isAdmin(String key) {
		return Objects.equals(key, "adminKey") || Objects.equals(key, "yetAnotherKey");
	}
}
