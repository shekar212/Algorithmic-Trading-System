package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Config.JwtUtil;
import com.example.demo.Services.CustomUserDetailsService;
import com.example.demo.Services.UserService;
import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.RegisterDto;

@RestController
@CrossOrigin
public class SignOnController {

	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping(value="/api/register")
	public ResponseEntity<String> registeration(@RequestBody RegisterDto cred) throws Exception{
		ResponseEntity<String> response=new ResponseEntity<String>("{\"status\":\"registered\"}",new HttpHeaders(),HttpStatus.OK);
		
		boolean result=userService.register(cred);
		
		if(!result) {
			response=new ResponseEntity<String>("{\"status\":\"already registered\"}",new HttpHeaders(),HttpStatus.OK);
		}
			
		return response;
		
		
	}

	@RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
				); 
			}
			catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password", e);
		}

		

		final UserDetails userDetails = userDetailsService
		.loadUserByUsername(authenticationRequest.getEmail());

		String name=userService.getEmployeeNameByEmail(userDetails.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt,name));
	
	}
}