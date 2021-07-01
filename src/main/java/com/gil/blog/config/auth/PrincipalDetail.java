package com.gil.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gil.blog.model.User;

import lombok.Getter;

//스프링 시큐리티가 로그인할때 가로채서 로그인을 진행하고 완료가되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장한다.
@Getter
public class PrincipalDetail implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user; //컴포지션( 객체를 품고 있는 것)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	
	//계정이 만료되지 않았는지 리털한다.(true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정 잠김 유무
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호가 만료되었는지
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정 활성화가 되어있는지
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	//계정 권한 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors=new ArrayList<>();		
		collectors.add(() -> {
			return "ROLE_" + user.getRole();
		});

		return collectors;
	}
}
