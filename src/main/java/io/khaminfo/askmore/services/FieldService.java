package io.khaminfo.askmore.services;

import java.security.Principal;




import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.khaminfo.askmore.domain.Field;
import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.domain.Subject;
import io.khaminfo.askmore.exceptions.AccessException;
import io.khaminfo.askmore.payload.ScriptToDropBox;
import io.khaminfo.askmore.repositories.FieldRepository;
import io.khaminfo.askmore.repositories.SubjectRepository;
import io.khaminfo.askmore.repositories.UserRepository;

@Service
public class FieldService {
	@Autowired
	private FieldRepository fieldRepository;
	@Autowired
    private UserRepository userRepository;	
	@Autowired
	private  SubjectRepository subjectRepository;

	public Field add( Field field, Principal principal) {
		Person user = userRepository.findByUsername(principal.getName());
		if(user.getType()!=1) {
			throw new AccessException("Access Denied!!!!");
		}
		try {
			Field fld = fieldRepository.save(field);
			ScriptToDropBox.change = true;
			return fld;
		}catch (Exception e) {
			throw new AccessException("Please Choose Another Name");
		}
		
	   	
	}

	public Iterable<Field> getAllField() {
		return fieldRepository.findAll();
	}

	public void archive(long id, Principal principal) {
		Person user = userRepository.findByUsername(principal.getName());
		if(user.getType()!=1) {
			throw new AccessException("Access Denied!!!!");
		}
		Field field = fieldRepository.getById(id);
		boolean newState = !field.isIs_arrchived();
		
		
	
		
		if(fieldRepository.updateFiedState(id, newState) != 1)
			throw new AccessException("Something went Wrong!!");
		ScriptToDropBox.change = true;
		
	}

	public void delete(long id, Principal principal) {
			// TODO Auto-generated method stub
			Person user = userRepository.findByUsername(principal.getName());
			if(user.getType()!=1) {
				throw new AccessException("Access Denied!!!!");
			}
		   fieldRepository.deleteById(id);	
		ScriptToDropBox.change = true;
	}

	public Subject addSubject(long id_field, @Valid Subject subject, Principal principal) {
		Person user = userRepository.findByUsername(principal.getName());
		if(user.getType()!=1) {
			throw new AccessException("Access Denied!!!!");
		}
		try {
			Field field = fieldRepository.getById(id_field);
			if(field.isIs_arrchived())
				throw new AccessException("This field is archived Please Choose Another Field");
				
			subject.setField(field);
			Subject s = subjectRepository.save(subject);
			ScriptToDropBox.change = true;
			return s;
		}catch (Exception e) {
			if(e.getClass() == AccessException.class)
				throw e;
			throw new AccessException("Please Choose Another Name");
		}
	}

	public Iterable<Subject> getAllSubjects() {
		return subjectRepository.findAll();
	}

	public void deleteSubject(long id, Principal principal) {
		Person user = userRepository.findByUsername(principal.getName());
		if(user.getType()!=1) {
			throw new AccessException("Access Denied!!!!");
		}
	   subjectRepository.deleteById(id);
		ScriptToDropBox.change = true;
		
	}

}
