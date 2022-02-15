package io.khaminfo.askmore.web;

import java.io.IOException;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.domain.Teacher;
import io.khaminfo.askmore.payload.JWTLoginSuccessResponse;
import io.khaminfo.askmore.payload.LoginRequest;
import io.khaminfo.askmore.security.JWTTokenProvider;
import io.khaminfo.askmore.security.SecurityConstants;
import io.khaminfo.askmore.services.MapValidationErrorService;
import io.khaminfo.askmore.services.UserService;
import io.khaminfo.askmore.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private MapValidationErrorService mapErrorService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator validator;
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = mapErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()

				));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}

	@PostMapping("/register/")
	public ResponseEntity<?> registerUser(@Valid @RequestBody String req, BindingResult result)
			throws JsonParseException, JsonMappingException, IOException {
		// validator.validate(user, result);
		
		Person user = null;
		ObjectMapper mapper = new ObjectMapper();
     
			user = mapper.readValue(req, Teacher.class);

		validator.validate(user, result);
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
      
		return new ResponseEntity<Person>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/confirm/{id}/{code}")
	public ResponseEntity<?> registerUser(@PathVariable long id, @PathVariable String code) {
		System.out.println("id  "+id +"  "+code);
		int result = userService.confirmUser(id, code);
		return new ResponseEntity<String>(result == 1 ? HttpStatus.OK : HttpStatus.NOT_MODIFIED);
	}
	

	@PostMapping("/logout/{username}")
	public ResponseEntity<?> logoutUser( @PathVariable String username) {
		System.out.println("logout controller "+ username);
		userService.logoutUser(username);	
		return new ResponseEntity<>( HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		return new ResponseEntity<List<Object[]>>( userService.getAllUsers(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable long id ) {
		return new ResponseEntity<Person>( userService.getUserById(id),HttpStatus.OK);
	}

	




}
