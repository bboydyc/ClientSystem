package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	private int status;
	private String message;
	private T data;
	
	//成功回應
	public static <T> ApiResponse success(String message, T data){
		return new ApiResponse<T>(200,message,data);
	}
	//失敗回應
	public static <T> ApiResponse<T> error(int status,String message){
		return new ApiResponse<T>(status, message,null);
	}
}
