package com.gil.blog.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gil.blog.model.RoleType;
import com.gil.blog.model.User;
import com.gil.blog.repository.UserRepository;

@RestController
public class DummyContorllerTest {

	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		
		return userRepository.findAll();
	}
	
	//한페이지당 두 건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> paginUser = userRepository.findAll(pageable);
		
		List<User> users = paginUser.getContent();
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음
	//http://localhost:8088/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4 를 찾으면 내가 데이터베이스에서 못 찾아올때 user가 null이 될 것 아냐?
		// 그러면 null값을 리턴할텐데 문제가 되지 않겠니?
		//optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해
		//.get() 은 반환받을때 데이터가 없으면 null값을 그대로 받아온다
		
		//람다식
		/* User user = userRepository.findById(id).orElseThrow(()-> {
		 * 	return new IllegalArgumentException("해당사용자는 없습니다.");
		 * });
		 */
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 사용자는 없습니다. : " + id);
			}
		});
		//요청 : 웹브라우저
		//user 객체 = 자바 오브젝트
		//변환 (웹브라우저가 이해할 수 있는 데이터로 변환) -> json
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 return을 하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에 던져준다.
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {		
		System.out.println("id : " +user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try{
			userRepository.deleteById(id);
		
		}catch(EmptyResultDataAccessException e) {
			return "데이터가 없습니다." + id;
		}
		
		return "삭제되었습니다. id" + id;
		
	}
	
	
	//save함수는 id를 전달하지 않으면 insert해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터 있을 때 update
	// save함수는 id를 전달하면 해당 id에 대한 데이터 없을 때 때 insert
	@Transactional //함수 종료시 자동 커밋
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정을 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(requestUser);
		
		//더티체킹
		return user;
	}
}
