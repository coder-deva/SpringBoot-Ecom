package com.springboot.ECommerce;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.ECommerce.exception.ResourceNotFoundException;



@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception = RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
		Map<String, String> map = new HashMap<>();
		map.put("Message", e.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(map);
	}
	
	
	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<?> handleResponseNotFoundException(ResourceNotFoundException e){
		
		 Map<String, String> map = new HashMap<>();
		 map.put("Message",e.getMessage());
		
		return ResponseEntity.
				status(HttpStatus.NOT_FOUND)
				.body(map);
	}

}

