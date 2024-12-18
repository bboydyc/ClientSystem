package com.example.demo.exception;

@SuppressWarnings("serial")
public class ClientAlreadyExistException extends ClientException{
	public ClientAlreadyExistException(String message) {
		super(message);
		
	}
}
