package com.gil.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 ->응답(HTML 파일)
//@Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG="HttpControllerTest:";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//@Builder를 사용하면 순서를 안지켜도 됨
		Member m = Member.builder().username("gilsang").password("1234").email("email@eamil.com").build(); 
				System.out.println(TAG + "getter : "+ m.getUsername());
				m.setUsername("gil");
				System.out.println(TAG + "setter : "+ m.getUsername());
				return "lombok test 완료";
	}
	
	//인터넷 브라우저 요청은 get요청만 할 수 있다. [?id=1&username=gil&password=1&email=aaa@email.com]
	//http://localhost:8088/http/get(select)
	@GetMapping("/http/get")
	public String getTest(Member m) {		
		return "get 요청:"+m.getId() + ", " +m.getUsername() + ", " +m.getPassword()+ ", "+m.getEmail();
	} 
	
	//http://localhost:8088/http/post(insert) --> text/plain, json/applictaion
	@PostMapping("/http/post") 
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트)가 값을 매핑해준다.
		return "post 요청:"+m.getId() + ", " +m.getUsername() + ", " +m.getPassword()+ ", "+m.getEmail();
	}

	//http://localhost:8088/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+ m.getId() +" , " + m.getPassword();
	}

	//http://localhost:8088/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
