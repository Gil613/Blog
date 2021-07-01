package com.gil.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gil.blog.dto.ReplySaveRequestDto;
import com.gil.blog.model.Board;
import com.gil.blog.model.User;
import com.gil.blog.repository.BoardRepository;
import com.gil.blog.repository.ReplyRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageAble) {

//		Page<Board> pageBoards =
		return boardRepository.findAll(pageAble); // List<>와 Page<> 는 넘겨주는 세부 값이 다르다.
//		List<Board> boards = pageBoards.getContent();
	}

	@Transactional(readOnly=true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});

	}
	
	@Transactional
	public void 글삭제하기(int id) {
		
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 수정하기 실패 : 아이디를 찾을 수 없습니다.");
					}); //영속화
		board.setTitle(requestBoard.getTitle()); //해당함수 종료시(Service가 종료될때) 트랜잭션이 종료되고 더티체킹 실행 - 자동 업데이트가 flush 
		board.setContent(requestBoard.getContent());
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		
	replyRepository.deleteById(replyId);
		
	}
	
	
}
