//package online.sumitakoliya.photoapp.api.users.security;
//
//import online.sumitakoliya.photoapp.api.users.service.UsersService;
//import online.sumitakoliya.photoapp.api.users.shared.UserDto;
//import online.sumitakoliya.photoapp.api.users.ui.model.LoginRequestModel;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//
//import javax.crypto.*;
//import javax.crypto.spec.SecretKeySpec;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.jsonwebtoken.Jwts;
//
//import org.springframework.security.core.userdetails.User;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//	
//	private UsersService usersService;
//	private Environment env;
//
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//			throws AuthenticationException {
//		try {
//			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
//
//			return getAuthenticationManager().authenticate(
//					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public AuthenticationFilter(AuthenticationManager authenticationManager, UsersService usersService, Environment env) {
//		super(authenticationManager);
//		this.usersService = usersService;
//		this.env = env;
//	}
//	
//	@Override
//	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, 
//			FilterChain chain, Authentication auth) throws IOException, ServletException{
//		
//		String userName = ((User)auth.getPrincipal()).getUsername();
//		UserDto userDetails = usersService.getUserDetailsByEmail(userName);
//		String tokenSecret = env.getProperty("token.secret");
//		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
//		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
//		
//		Instant now = Instant.now();
//		String token = Jwts.builder()
//		.subject(userDetails.getUserId().toString())
//		.expiration(Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
//		.issuedAt(Date.from(now))
//		.signWith(secretKey)
//		.compact();
////		.setSubject(userDetails.getUserId().toString())
//		
//		res.addHeader("token", token);
//		res.addHeader("userId", userDetails.getUserId().toString());
//		
//		
//	}
//	
//}

package online.sumitakoliya.photoapp.api.users.security;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;

import online.sumitakoliya.photoapp.api.users.service.UsersService;
import online.sumitakoliya.photoapp.api.users.shared.UserDto;
import online.sumitakoliya.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UsersService usersService;
	private Environment environment;

	public AuthenticationFilter(UsersService usersService, Environment environment,
			AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.usersService = usersService;
		this.environment = environment;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String userName = ((User) auth.getPrincipal()).getUsername();
		UserDto userDetails = usersService.getUserDetailsByEmail(userName);
		String tokenSecret = environment.getProperty("token.secret");
		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

		Instant now = Instant.now();

		String token = Jwts.builder().setSubject(userDetails.getUserId().toString())
				.setExpiration(
						Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
				.setIssuedAt(Date.from(now)).signWith(secretKey, SignatureAlgorithm.HS512).compact();

		res.addHeader("token", token);
		res.addHeader("userId", userDetails.getUserId().toString());
	}
}

