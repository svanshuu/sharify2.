package com.shayarify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shayarify.config.JwtProvider;
import com.shayarify.model.User;
import com.shayarify.repository.UserRepository;
import com.shayarify.request.LoginRequest;
import com.shayarify.response.AuthResponse;
import com.shayarify.service.CustomUserDetailsService;
import com.shayarify.service.UserService;

@RestController
@RequestMapping("/auth") 
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {

		User isExist = userRepository.findByEmail(user.getEmail());
		if (isExist != null) {
			throw new Exception("This email is already used with another account");
		}

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setTermsAccepted(user.isTermsAccepted());

		User savedUser = userRepository.save(newUser);

		// Create an Authentication object for JWT generation
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generate token using the JwtProvider
		String token = JwtProvider.generateToken(authentication);
		System.out.println("Generated JWT token: " + token);

		return new AuthResponse(token, "Register Success");
	}

	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

		// Generate token using the JwtProvider
		String token = JwtProvider.generateToken(authentication);
		System.out.println("Generated JWT token: " + token);

		return new AuthResponse(token, "login Success");
	}

	private Authentication authenticate(String email, String password) {

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {// use for check encode password and
																			// password given by frontend is same or not
			throw new BadCredentialsException("password not match");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
