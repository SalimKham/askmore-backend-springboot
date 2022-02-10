package io.khaminfo.askmore.validator;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.exceptions.AccessException;


@Component
public class UserValidator implements Validator{

	@Autowired
	 BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		Person user = (Person)object;
	    try {
		if(user.getPassword().length()< 6) {
			throw new AccessException("Password mast be at least 6 characters");
		}else if(!user.getPassword().equals(user.getConfirmPassword())) {
			
			throw new AccessException("Passwords must mutch");
		}
		
	    }catch(Exception e) {
	    	if (e.getClass() == AccessException.class)
	    		throw e;
	    	else
	    		throw new AccessException("Please check your inputs");
	    }
	}
	

}
