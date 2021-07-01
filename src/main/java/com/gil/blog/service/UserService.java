package com.gil.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gil.blog.model.RoleType;
import com.gil.blog.model.User;
import com.gil.blog.repository.UserRepository;

@Service
public class UserService {

	@Value("{cos.key}")
	private String cosKey;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 원문
		String encPassword = encoder.encode(rawPassword); //원문을 해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원정보수정(User user) {
		//수정시에는 영속성컨텍스트에 User오브젝트를 영속화시키고, 영속화된 User오브젝트를 수정
		//select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서.
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()-> {
					return new IllegalArgumentException("회원정보가 없습니다.");
				});
		
		//validation check.
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		
		
		//회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션이 종료 = commit이 자동으로 진행
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
	}
	
	@Transactional
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElse(null);
		return user;
	}
	
}
