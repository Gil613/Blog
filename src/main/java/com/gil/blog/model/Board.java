package com.gil.blog.model;



import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 넘버링 전략
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 <html> 태그가 섞여서 디자인 됨.
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //Many(=Board) , One(=User) 한명의 유저는 여러개의 게시글을 쓸 수 있다. <-> OneToOne = 한명의 유저는 하나의 게시글만 쓸 수 있다.
	@JoinColumn(name="userId") //필드값을 userid로 사용한다. --> private int userid;
	private User user; //DB는 오브젝트를 저장할 수 없기때문에 FK을 생성해서 사용, ORM은 오브젝트를 저장할 수 있다. 자바 프로그램에서 데이터베이스의 자료형에 맞춰서 테이블을 만든다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //mappedBy -> 연관관계의 주인이 아니다 DB에 칼럼을 만들지 마세요 (FK가 아니다)
	//@JoinColumn(name = "replyId") 필요 없음. reply의 데이터는 2개 이상이기 때문에 칼럼으로 만들 수 없음 데이터베이스의 1정규화 원자성에 위배 됨 
	@JsonIgnoreProperties({"board","user"}) //무한 참조 방지
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
