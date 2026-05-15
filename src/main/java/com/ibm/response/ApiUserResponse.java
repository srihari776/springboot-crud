package com.ibm.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserResponse {
	
	private boolean sucess;
	private String message;
	private Object data;

}
