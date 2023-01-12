package com.ram.userService.Model;

public class UpdatePassRequest {
	
	private String username;
	private String password;
	public UpdatePassRequest() {
		
		// TODO Auto-generated constructor stub
	}
	
	public UpdatePassRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
