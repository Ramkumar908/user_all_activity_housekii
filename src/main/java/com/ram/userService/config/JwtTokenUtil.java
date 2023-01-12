package com.ram.userService.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	public String getUsernameFromToken(String token) {
		System.out.println("the token is"+token);
		return getClaimFromToken(token, Claims::getSubject);
	}

	//retrieve expiration date from jwt token
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}

		
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		}
		
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			System.out.println("the date while checking for expiration is "+expiration);
			return expiration.before(new Date());
		}
		
		public String generateToken(UserDetails userDetails) {
			System.out.println("I am in generate token function and we get usrdetails for generate token is"+userDetails);
			Map<String, Object> claims = new HashMap<>();
			return doGenerateToken(claims, userDetails.getUsername());
		}

		
		private String doGenerateToken(Map<String, Object> claims, String subject) {

			System.out.println("we get subject in dogenerate method is"+subject);
			System.out.println("we get Secret  in dogenerate method is"+secretKey);

			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secretKey).compact();
		}
		
		//validate token
		public Boolean validateToken(String token, UserDetails userDetails) {
			System.out.println("we get token  in validateToken method is"+token);
			final String username = getUsernameFromToken(token);
			System.out.println("get username from token in validate method is"+username);
			System.out.println("Username from userdetail is "+userDetails.getUsername());
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}	

}
