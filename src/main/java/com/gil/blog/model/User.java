package com.gil.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert insert시에 null인 필드를 제외 시켜 준다.
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; //아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//DB는 RoleType이 뭔지 모르기 때문에 ENUM의 데이터형을 설정해준다.
	@Enumerated(EnumType.STRING)
	//@Columndefalut("'user'")
	private RoleType role; //오타 및 고정 된 값을 넣기 위해 Enum을 쓰는게 좋다. //ADMIN, USER 
	
	private String oauth; //kakao, google
	
	@CreationTimestamp //시간이 자동으로 입력
	private Timestamp createDate;
}
