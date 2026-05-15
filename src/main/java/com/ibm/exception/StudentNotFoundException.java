package com.ibm.exception;

public class StudentNotFoundException extends RuntimeException {
	
	private int id;
	
	public StudentNotFoundException(int id ){
		super("student not found for id : " + id);
		this.id = id;
	}
    public StudentNotFoundException(String message) {
        super(message);
    }
	
	public int getID() {
		return id;
	}

}
