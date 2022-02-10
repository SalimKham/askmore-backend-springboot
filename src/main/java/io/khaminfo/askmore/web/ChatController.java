package io.khaminfo.askmore.web;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.khaminfo.askmore.domain.Chat;
import io.khaminfo.askmore.services.ChatService;
import io.khaminfo.askmore.services.MapValidationErrorService;

@RestController
@RequestMapping("/api/chat/")
public class ChatController {
	@Autowired
	private ChatService chatService;
	@Autowired
	private MapValidationErrorService mapErrorService;
	@PostMapping("/send/")
	public ResponseEntity<?> sendMessge (@Valid @RequestBody Chat message, BindingResult result)
			{
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
		
		return new ResponseEntity<Chat>(chatService.sendMessage(message), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMessages(@PathVariable long id ) {
		return new ResponseEntity<List<Object[]>>( chatService.getMessages(id ),HttpStatus.OK);
	}
	

}
