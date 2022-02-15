package io.khaminfo.askmore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
@Entity
public class Groupe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "name required!")
	@Column(unique = true)
	private String name;
	private int state = 1;
	int nbr_users;
	private long owner;
	private String users = "";
	
	private String acceptedUsers = "";
	
	

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAcceptedUsers() {
		return acceptedUsers;
	}
	public void setAcceptedUsers(String acceptedUsers) {
		this.acceptedUsers= acceptedUsers;
	}
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNbr_users() {
		return nbr_users;
	}
	public void setNbr_users(int nbr_users) {
		this.nbr_users = nbr_users;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	
	


}
