package com.ibm.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.response.ApiUserResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiUserResponse> handleValidationException(MethodArgumentNotValidException ex){
		
		Map<String , String > map = new HashMap<>();
		for(FieldError er : ex.getBindingResult().getFieldErrors()) {
			map.put(er.getField(), er.getDefaultMessage());
		}
		
		ApiUserResponse api = new ApiUserResponse(
				false,
				"validation failed",
				map
				);
		
		return new ResponseEntity<ApiUserResponse>(api,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<ApiUserResponse> handleStudentNotFundException(StudentNotFoundException ex){
		
		ApiUserResponse api = new ApiUserResponse(
				false,
				ex.getMessage(),
				null
				);
		return new ResponseEntity<ApiUserResponse>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiUserResponse> handleExceptions(Exception ex){
		ApiUserResponse api = new ApiUserResponse(
				false,
				ex.getMessage(),
				null
				);
		return new ResponseEntity<ApiUserResponse>(api,HttpStatus.INTERNAL_SERVER_ERROR); 
	}

}
