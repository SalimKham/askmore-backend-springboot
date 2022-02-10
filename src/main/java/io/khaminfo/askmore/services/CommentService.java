package io.khaminfo.askmore.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.khaminfo.askmore.domain.Comment;
import io.khaminfo.askmore.domain.LearningEvent;
import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.exceptions.AccessException;
import io.khaminfo.askmore.payload.ScriptToDropBox;
import io.khaminfo.askmore.repositories.CommentRespository;
import io.khaminfo.askmore.repositories.LearningEventRepository;
import io.khaminfo.askmore.repositories.TutorialRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRespository commentRepository;
	@Autowired
	private LearningEventRepository learningEventRepository;
	@Autowired
	private TutorialRepository tutorialRepository;
	public Comment addComment( long id_parent,Comment comment) {
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    LearningEvent parent  = learningEventRepository.getById(id_parent);
	    if(parent == null)
	    	throw new AccessException("Some Thing Wents Wrong!");
		  comment.setUser(user);
		
			  tutorialRepository.updateNbrComments(parent.getId());
		
		  comment.setParent(parent);
		  try {
			  Comment cmt = commentRepository.save(comment);
			  ScriptToDropBox.change = true;
		  return cmt;
		  }catch (Exception e) {
			  throw new AccessException("Some Thing Wents Wrong!");
		}
	}
	
	public Iterable<Comment> getAll(long idEvent) {
		LearningEvent parent  = learningEventRepository.getById(idEvent);
		return parent.getComments();
	}

	public void  delete(long id) {
		commentRepository.deleteById(id);
		ScriptToDropBox.change = true;
	}


}
