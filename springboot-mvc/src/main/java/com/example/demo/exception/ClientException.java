package com.example.demo.exception;
@SuppressWarnings("serial")
public class ClientException extends RuntimeException{
	public ClientException(String message) {
		super(message);
	}
}
