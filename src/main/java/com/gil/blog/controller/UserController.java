package com.gil.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gil.blog.model.KakaoProfile;
import com.gil.blog.model.OAuthToken;
import com.gil.blog.model.User;
import com.gil.blog.service.UserService;

//인증이 안된 사용자들이 출입 할 수 있는 경로를 /auth/** 허용
//주소가 그냥 / 이면 index.jsp
//static 이하에 있는 /js/**, /css/**, /image/** 등등 허용

@Controller
public class UserController {
	
	@Value("{cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	@GetMapping("/auth/joinForm")
	public String joinForm() {

		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {

		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {

		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallBack(String code) {
		// POST 방식으로 Key = Value 데이터를 요청 (카카오톡으로)
				// HttpsURLConnection
				// Retrofit2
				// OkHttp
				// RestTemplate
				RestTemplate rt = new RestTemplate();

				// HttpHeader 오브젝트 생성
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

				// HttpBody 오브젝트 생성
				MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

				params.add("grant_type", "authorization_code");
				params.add("client_id", "d423f54ea1829b65ccf4895e27242fd3");
				params.add("redirect_uri", "http://localhost:8088/auth/kakao/callback");
				params.add("code", code);

				// HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

				// Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답 받음
				ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
						kakaoTokenRequest, String.class);

				ObjectMapper obMapper = new ObjectMapper();
				OAuthToken oAuthToken = null;

				try {
					oAuthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				RestTemplate rt2 = new RestTemplate();

				// HttpHeader 오브젝트 생성
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
				headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

				// HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

				// Http 요청하기 - POST 방식으로 - 그리고 response 변수에 응답 받음
				ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
						kakaoProfileRequest, String.class);

				ObjectMapper obMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;

				try {
					kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		
		//UUID란 중복되지 않은 어떤 특정 값을 만들어내는 알고리즘 UUID garbagePassword = UUID.randomUUID();
		
				
				User kakaoUser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail() + "-" + kakaoProfile.getId())
						.password(cosKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				
				User originUser = userService.회원찾기(kakaoUser.getUsername());
				
				if(originUser.getUsername() == null) {
					userService.회원가입(kakaoUser);
		}
		//로그인처리
		  Authentication authentication 
		  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		  SecurityContextHolder.getContext().setAuthentication(authentication);
		

		return "redirect:/";
	}
}
