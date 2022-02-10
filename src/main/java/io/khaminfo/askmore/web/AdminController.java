package io.khaminfo.askmore.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.khaminfo.askmore.domain.Field;
import io.khaminfo.askmore.domain.Subject;
import io.khaminfo.askmore.services.FieldService;
import io.khaminfo.askmore.services.MapValidationErrorService;
import io.khaminfo.askmore.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private FieldService fieldService;
	@Autowired
	private MapValidationErrorService mapErrorService;
	@PostMapping("/activate/{id}")
	
	public ResponseEntity<?> activateUser(@PathVariable long id , Principal principal) {
		userService.ActivateUser(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/blockUnblock/{id}")
	public ResponseEntity<?> blockUnblock(@PathVariable long id , Principal principal) {
		userService.blockUnblock(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id , Principal principal) {
		userService.deleteUser(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/addField/")
	public ResponseEntity<?> addField (@Valid @RequestBody Field field,Principal principal, BindingResult result)
			{
	
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
		
	
		return new ResponseEntity<Field>(fieldService.add(field, principal), HttpStatus.CREATED);
	}
	@PostMapping("/field/addSubject/{id_field}")
	public ResponseEntity<?> addSubject (@Valid @RequestBody Subject subject,@PathVariable long id_field,Principal principal, BindingResult result)
			{
	
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
		
	
		return new ResponseEntity<Subject>(fieldService.addSubject(id_field , subject, principal), HttpStatus.CREATED);
	}
	
	@GetMapping("/field/all")
	public ResponseEntity<?> getAllFields() {
		return new ResponseEntity<Iterable<Field>>( fieldService.getAllField(),HttpStatus.OK);
	}
	
	@GetMapping("/field/subject/all")
	public ResponseEntity<?> getAllSubjects() {
		return new ResponseEntity<Iterable<Subject>>( fieldService.getAllSubjects(),HttpStatus.OK);
	}


	
	@PostMapping("/field/archive/{id}")
	public ResponseEntity<?> archiveField(@PathVariable long id , Principal principal) {
		fieldService.archive(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	@DeleteMapping("/field/{id}")
	public ResponseEntity<?> deleteField(@PathVariable long id , Principal principal) {
		fieldService.delete(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	@DeleteMapping("/field/subject/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable long id , Principal principal) {
		fieldService.deleteSubject(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
}
