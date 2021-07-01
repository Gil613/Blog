package com.gil.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // --> @Getter + @Setter
@NoArgsConstructor //빈 생성자
//@RequiredArgsConstructor final이 붙은 객체에 대해서만 생성자 적용 
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
