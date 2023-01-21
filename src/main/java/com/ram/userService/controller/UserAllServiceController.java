package com.ram.userService.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.ram.userService.Model.JwtRequest;
import com.ram.userService.Model.JwtResponse;
import com.ram.userService.Model.UpdatePassRequest;
import com.ram.userService.Model.forgotPassRequest;
import com.ram.userService.config.JwtTokenUtil;
import com.ram.userService.service.ForgetPassUserService;
import com.ram.userService.service.JwtUserDetailService;
import com.ram.userService.service.MailerifyUser;
import com.ram.userService.service.UpdatePassService;
import com.ram.userService.service.UserRegisterService;
import com.ram.userService.util.ResponseStatus;

@RestController
public class UserAllServiceController {
	
	
	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	
	@Autowired 
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	ForgetPassUserService  forgotService;
	
	
	@Autowired
	MailerifyUser mailService;
	
	
	@Autowired
	UpdatePassService updatePassService;
	
	
	@Autowired
	UserRegisterService service;
	
	@RequestMapping(value="/",method= {RequestMethod.GET})
	public String TestingOfController()
	{
		return "HelloTester all good and Best";
	}
	@CrossOrigin("*")
	@RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtAuthenticationRequest)
			throws Exception {

		authenticate(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
		JwtResponse response=new JwtResponse();
		response.setUserName(userDetails.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		response.setJwtToken(token);
		
		response.setUserType("User");
		return ResponseEntity.ok(response);

	}
	private void authenticate(String username, String password) throws Exception {
		try {
			System.out.println(username + " password " + password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User Disabled ", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials", e);
		}

	}
	@CrossOrigin("*")
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public ResponseEntity<ResponseStatus> saveUser(@RequestBody com.ram.userService.Model.User user,HttpServletRequest request) throws Exception {
          
		ResponseStatus registerStatus=null;
		try
		{
		System.out.println("username "+user.toString());
		 registerStatus=service.getUserRegister(user);
		 System.out.println("Status "+registerStatus.getResponseCode());
		
		 HttpSession session=request.getSession();
		 session.setAttribute("username",user.getUsername());
		}catch(InternalServerError e)
		{
			registerStatus.setResponseCode(500);
			registerStatus.setResponseStatus("InternalServerError");
			registerStatus.setStatusMessage("Something went wrong in the Server side ");
			
		}
	
		return ResponseEntity.ok(registerStatus);
	}
	
	@RequestMapping(value="/mail/verify/{otpCode}/{username}",method= {RequestMethod.GET})
	public ResponseEntity<ResponseStatus> getMailVerifyUser(@PathVariable("otpCode") String otpCode,@PathVariable("username") String username)
	{
		System.out.println("Get Otp from User is"+otpCode);
		
		ResponseStatus statusOfOtp=new ResponseStatus();
		statusOfOtp=mailService.getMailVerified(otpCode,username);
					
		return  ResponseEntity.ok(statusOfOtp);
	}
	
	@CrossOrigin("*")
	@RequestMapping(value="/user/forgotPassword",method= {RequestMethod.POST})
	public ResponseEntity<ResponseStatus>  forgetUserPass(@RequestBody forgotPassRequest request)
	{
	     ResponseStatus status=forgotService.getUserForhetPass(request.getEmail());
		return ResponseEntity.ok(status);
	}
	
	@RequestMapping(value="/verify/user/password/{otpCode}/{username}",method= {RequestMethod.GET})
	public ResponseEntity<ResponseStatus>  verifyUserForrupdatePassword(@PathVariable("otpCode") String otpCode,@PathVariable("username") String username)
	{
		ResponseStatus status=mailService.getMailVerified(otpCode,username);
		return  ResponseEntity.ok(status);
	}
	
	
	@RequestMapping(value="/user/Update/password",method= {RequestMethod.POST})
	public ResponseEntity<ResponseStatus>  updateUserPass(@RequestBody UpdatePassRequest request)
	{
		System.out.println("username "+request.getUsername()+" pass"+request.getPassword());
		ResponseStatus status=updatePassService.updateUserPass(request);
		return  ResponseEntity.ok(status);
	}
	
	

}
