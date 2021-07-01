package com.gil.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	//localhost:8088/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일리턴 기본경로 : src/main/resources/static
		//리턴명 : home.html 일경우 경로가 src/main/resources/statichome.html 을 리턴해준다.
		//그래서 리턴명에 '/'가 꼭 들어가야한다. /home.html
		return "/home.html";
	}
	
	@GetMapping("temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		//풀네임 : /WEB-INF/views/test.jsp
		return "test";
	}
}
