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

import io.khaminfo.askmore.domain.Groupe;
import io.khaminfo.askmore.domain.Student;
import io.khaminfo.askmore.services.GroupeService;
import io.khaminfo.askmore.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/groupe")
public class GroupeController {
	@Autowired
	private MapValidationErrorService mapErrorService;
	@Autowired
	private GroupeService groupeService;
	@PostMapping("/add/")
	public ResponseEntity<?> addGroupe (@Valid @RequestBody Groupe groupe,Principal principal, BindingResult result)
			{
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
		
		return new ResponseEntity<Groupe>(groupeService.addGroupe(groupe, principal), HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllGroupes() {
		return new ResponseEntity<Iterable<Groupe>>( groupeService.getAllGroupes(),HttpStatus.OK);
	}
	@GetMapping("/ByTeacher")
	public ResponseEntity<?> getTeacherGroupes() {
		return new ResponseEntity<Iterable<Groupe>>( groupeService.getTeacherGroupes(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable long id , Principal principal) {
		groupeService.deleteGroupe(id, principal);	
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/Activate/{id}")
	public ResponseEntity<?> ActivateGroupe(@PathVariable long id , Principal principal) {
		System.out.println("Controller : update groupe state");
		groupeService.updateState(id, 1, principal);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	@PostMapping("/update/{id}/{newState}")
	public ResponseEntity<?> updateState(@PathVariable long id , @PathVariable int newState, Principal principal) {
		groupeService.updateState(id, newState, principal);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/acceptStudent/{idGroupe}/{idStudent}")
	public ResponseEntity<?> acceptStudent(@PathVariable long idGroupe , @PathVariable long idStudent, Principal principal) {
		groupeService.acceptStudentInGroupe(idGroupe, idStudent, principal);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/join/{id}")
	public ResponseEntity<?> joinGroupe(@PathVariable long id) {
		
		return new ResponseEntity<String>( groupeService.JoinGroupe(id), HttpStatus.OK);
	}
	
	@PostMapping("/leave/{id}/{idStudent}")
	public ResponseEntity<?> leaveGroupe(@PathVariable long id  , @PathVariable long idStudent) {
		groupeService.leaveGroupe(id, idStudent);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getGroupe(@PathVariable long id ) {
		return new ResponseEntity<Groupe> ( groupeService.getGroupe(id),HttpStatus.OK);
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<?> getStudents(@PathVariable long id ) {
		return new ResponseEntity<Iterable<Student>> ( groupeService.getAllStudentByGroupe(id),HttpStatus.OK);
	}

}
