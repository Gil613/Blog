package com.gil.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gil.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer> {
	
}

