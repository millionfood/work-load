package com.fmc.exception;

public class LocalFileException extends RuntimeException{
	public LocalFileException(String message) {super(message);}
	public LocalFileException(String message, Throwable cause) {super(message, cause);}
}
