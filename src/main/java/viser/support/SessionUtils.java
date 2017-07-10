package viser.support;

import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static boolean isEmpty(HttpSession session, String key) {

		// 세션 안에 key 값이 들어간 경우와 아닌 경우
		Object obj = session.getAttribute(key);
		if (obj == null) {
			return true;
		}
		return false;

	}

	public static String getStringValue(HttpSession session, String key) {
		// 세션 값이 존재할 경우의 키 값 추출
		if (isEmpty(session, key)) {
			return null;
		}

		return (String) session.getAttribute(key);
	}
}
