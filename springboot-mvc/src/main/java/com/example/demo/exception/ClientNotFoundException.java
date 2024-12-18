package com.example.demo.exception;
@SuppressWarnings("serial")
public class ClientNotFoundException extends ClientException{

	public ClientNotFoundException() {
		super("查無此客戶");
		
	}
	public ClientNotFoundException(String message) {
		super(message);
		
	}

}

	