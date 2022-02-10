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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.khaminfo.askmore.domain.QuestionnaryRequest;
import io.khaminfo.askmore.domain.Tutorial;
import io.khaminfo.askmore.services.MapValidationErrorService;
import io.khaminfo.askmore.services.TutorialService;

@RestController
@RequestMapping("/api/tutorial")
public class TutorialController {
	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private MapValidationErrorService mapErrorService;
	@PostMapping("/add/")
	public ResponseEntity<?> addTutorial(@RequestParam("subject") long subject ,@RequestParam("allowedGroupes") String allowedGroupes ,@RequestParam("tutorial")  String details , @RequestParam(name = "file" , required = false) MultipartFile file) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		
		Tutorial tutorial = mapper.readValue(details, Tutorial.class);
		return new ResponseEntity<Tutorial>(tutorialService.addTutorial(subject, allowedGroupes,tutorial, file),HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTutorial(@PathVariable long id ) {
		
		return new ResponseEntity<Tutorial>(tutorialService.getTutorialById(id),HttpStatus.OK);
	}
	
	@PostMapping("/addQuestionnary/{tutorial}")
	public ResponseEntity<?> addQuestionnary(@PathVariable("tutorial") long tutorial ,@Valid @RequestBody QuestionnaryRequest questionnary,  BindingResult result ){
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;
		
		String [] questionsArray = questionnary.getQuestions().split("sp_q");
		 String [] answersArry = questionnary.getAnswers().split("sp_ans");
		 System.out.println(questionsArray.length+"   "+answersArry.length);
		 tutorialService.addQuestionnary(tutorial, questionsArray, answersArry);
		 return null;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllTutorials() {
		return new ResponseEntity<Iterable<Tutorial>>( tutorialService.getAll(),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteQuestionnary/{id}")
	public void deleteQuestionnary(@PathVariable long id){
	       tutorialService.deleteQuestionnary(id);
		 
	}
	
	@DeleteMapping("/{id}")
	public void deleteTutorial(@PathVariable long id){
	       tutorialService.delete(id);
		 
	}

	
	

}
