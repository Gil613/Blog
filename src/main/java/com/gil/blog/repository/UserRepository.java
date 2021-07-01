package com.gil.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gil.blog.model.User;


//자동으로 bean등록이 된다.
//@Repository를 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
	//select * from user where username=?
	Optional<User> findByUsername(String username);
}




//JPA Naming 쿼리
//SELECT * FROM user WHERE username = ? AND password = ?;
//User findByUsernameAndPassword(String username,String password);

// 다른 방법
//	@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username,String password);