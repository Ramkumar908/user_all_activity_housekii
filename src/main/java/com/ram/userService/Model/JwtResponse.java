package com.ram.userService.Model;

import java.io.Serializable;

public class JwtResponse  implements Serializable{

	
	
	

	

	private static final long serialVersionUID = 1L;
	private  String jwtToken;
	private String userName;
	private String userType;
	
	
	
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String jwtToken, String userName, String userType) {
		this.jwtToken = jwtToken;
		this.userName = userName;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserType() {
		return userType;
	}

	public String getJwtToken() {
		return jwtToken;
	}


	 public  void setJwtToken(String JwtToken)
	 {
		  this.jwtToken=JwtToken;
	 }

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "JwtResponse [jwtToken=" + jwtToken + ", userName=" + userName + ", userType=" + userType + "]";
	}


	
	
	

}
