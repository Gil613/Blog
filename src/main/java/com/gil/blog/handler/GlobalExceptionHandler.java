package com.gil.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.gil.blog.dto.ResponseDto;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	//Exception이 발생했을때 함수 호출
	
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleException(Exception e) {
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); //500
	
	}
	
}
