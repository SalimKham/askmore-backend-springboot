package io.khaminfo.askmore.services;

import java.security.Principal;



import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.khaminfo.askmore.domain.Groupe;
import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.domain.Teacher;
import io.khaminfo.askmore.exceptions.AccessException;
import io.khaminfo.askmore.payload.ScriptToDropBox;
import io.khaminfo.askmore.repositories.GroupeRepository;
import io.khaminfo.askmore.repositories.TeacherRepository;
import io.khaminfo.askmore.repositories.UserRepository;

@Service
public class GroupeService {

	@Autowired
	private GroupeRepository groupeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	TeacherRepository teacherRepository;

	public Groupe addGroupe(@Valid Groupe groupe, Principal principal) {
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getType() != 3) {
			throw new AccessException("Access Denied!!!!");
		}
		try {

			groupe.setOwner( user.getId());
			groupe.setState(1);
			groupe.setUsers(user.getId()+","+user.getUsername()+"/");
			groupe.setAcceptedUsers(user.getId()+"/");
			Groupe gr = groupeRepository.save(groupe);
			Teacher t = teacherRepository.getById(user.getId());
			t.setJoinedGroupes(t.getJoinedGroupes()+""+groupe.getId()+"/");
			teacherRepository.save(t);
			ScriptToDropBox.change = true;
			return gr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AccessException("Please Choose Another Name");
		}

	}

	public Iterable<Person> getAllUsersByGroupe(long id) {

		try {

			Groupe groupe = groupeRepository.getById(id);
			if (groupe == null)
				throw new AccessException("No Groupe Found");
			String users =  "("+groupe.getUsers().replaceAll("/", ",")+")";
			return userRepository.getUsersByIds(users);
		} catch (Exception e) {
			throw new AccessException("SomeThingWentWrong");
		}
	}

	public Iterable<Groupe> getAllGroupes() {
		return groupeRepository.findAll();

	}

	public void deleteGroupe(long id, Principal principal) {
		// TODO Auto-generated method stub
		Person user = userRepository.findByUsername(principal.getName());
		Groupe groupe = groupeRepository.getById(id);
		if(groupe == null)
			throw new AccessException("Something went wrong");
		if (groupe.getOwner()!= user.getId())
			throw new AccessException("NO ACESS!");

		groupeRepository.deleteById(id);
		ScriptToDropBox.change = true;
	}

	public Groupe acceptStudentInGroupe(long id, long id_student) {
		// TODO Auto-generated method stub
		System.out.println("Now we are here");
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
			Groupe groupe = groupeRepository.getById(id);
			if (groupe.getOwner()!= user.getId())
				throw new AccessException("Access Denied!!!!");
			String newList = groupe.getAcceptedUsers() +   id_student+"/";
			groupe.setAcceptedUsers(newList);
			Teacher t = teacherRepository.getById(id_student);
			if(t == null){
				throw new AccessException("SomeThing Went Wrong");
			}
			t.setJoinedGroupes(t.getJoinedGroupes()+""+groupe.getId()+"/");
			teacherRepository.save(t);
			if (groupeRepository.updateAcceptedStudent(id, newList) != 1)
				throw new AccessException("No groupe Found");
			ScriptToDropBox.change = true;
			return groupe;
			
		

	}

	public Groupe JoinGroupe(long id_groupe) {
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getType() == 3) {
			Groupe groupe = groupeRepository.getById(id_groupe);
			 if(groupe == null)
				 throw new AccessException("Oops! Something went Wrong.");
			groupe.setUsers(groupe.getUsers() +user.getId()+","+user.getUsername()+"/");
			groupe.setNbr_users(groupe.getNbr_users()+1);
			groupeRepository.save(groupe);
			ScriptToDropBox.change = true;
			return groupe;
		}
		
		throw new AccessException("Oops! Something went Wrong.");
	}

	public Groupe leaveGroupe(long id_groupe, long idUser) {
		Teacher t = teacherRepository.getById(idUser);
		Groupe groupe = groupeRepository.getById(id_groupe);
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       if(t == null) {
    	   throw new AccessException("Something went wrong!");
       }
       if(t.getId() != user.getId() && groupe.getOwner() != user.getId())
    	   throw new AccessException("NO ACCESS!");
       
		String groupeUsers = groupe.getUsers().replace(idUser+","+t.getUsername()+"/", "");
	    groupe.setUsers(groupeUsers);
         groupe.setNbr_users(groupe.getNbr_users() - 1);
		if (groupe.getAcceptedUsers() != null) {
			groupe.setAcceptedUsers(groupe.getAcceptedUsers().replace( idUser+"/", ""));
		}
		groupeRepository.save(groupe);
		
		t.setJoinedGroupes(t.getJoinedGroupes().replace(id_groupe+"/", ""));
		teacherRepository.save(t);
		ScriptToDropBox.change = true;
       return groupe;
	}

	public void updateState(long id, int newState, Principal principal) {
		System.out.println("update groupe state");
		String validStates = "123";
		if (validStates.indexOf("" + newState) == -1)
			throw new AccessException("Please Use a valide stae (1 , 2 )");
		// TODO Auto-generated method stub
		Person user = userRepository.findByUsername(principal.getName());
		boolean access = true;
		switch (user.getType()) {

		case 3:
			Groupe groupe = groupeRepository.getById(id);
			if (groupe.getOwner()!= user.getId())
				access = false;
			break;
		case 2:
			access = false;
			break;

		default:
			break;
		}

		if (!access)
			throw new AccessException("Access Denied!!!!");

		if (groupeRepository.updateGroupeState(id, newState) != 1)
			throw new AccessException("No groupe Found");
		ScriptToDropBox.change = true;
	}

	public Iterable<Groupe> getTeacherGroupes() {
		Person user = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getType() == 3) {
			List<Groupe> groupes =  groupeRepository.getByOwnerId(user.getId());
			return groupes;
		}
		return null;
	}

	public Groupe getGroupe(long id) {
		// TODO Auto-generated method stub
		return groupeRepository.getById(id);
	}
}