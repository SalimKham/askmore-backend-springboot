package io.khaminfo.askmore.web;

import java.io.IOException;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.khaminfo.askmore.domain.Comment;
import io.khaminfo.askmore.services.CommentService;
import io.khaminfo.askmore.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	
	@Autowired
	private MapValidationErrorService mapErrorService;
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/add/{idEvent}")
	public ResponseEntity<?> addComment(@Valid @RequestBody Comment comment, @PathVariable long idEvent, BindingResult result)
			throws JsonParseException, JsonMappingException, IOException {
		// validator.validate(user, result);
		

		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
      
		return new ResponseEntity<Comment>(commentService.addComment(idEvent, comment), HttpStatus.CREATED);
	}
	
	@GetMapping("/all/{idEvent}")
	public ResponseEntity<?> getAll( @PathVariable long idEvent)
			 {

		return new ResponseEntity<Iterable<Comment	> >(commentService.getAll(idEvent), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete( @PathVariable long id)
			 {
            commentService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
