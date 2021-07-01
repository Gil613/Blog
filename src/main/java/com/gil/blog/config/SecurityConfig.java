package com.gil.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gil.blog.config.auth.PrincipalDetailService;

@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것(IoC관리)
@EnableWebSecurity // 시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC 적용 된다. 뭐가? 리턴하는 값
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	//시큐리티가 대신 로그인 할때 패스워드를 가로채는데 해당 패스워드가 무엇으로 해쉬가 돼서 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가 가능하다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // csrf토큰 비활성화 (테스트시 걸어두는게 좋음ㅁ)
				.authorizeRequests() // 요청을 할때
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**") 
				.permitAll() // 모두 접근 가능
				.anyRequest() // 다른 요청들은
				.authenticated() // 인증되어야돼
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
				.defaultSuccessUrl("/");
	}
}
