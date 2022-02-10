package io.khaminfo.askmore.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	@ManyToOne
	@JoinColumn(name="owner_id", updatable = false, nullable = false)
	private Teacher owner;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "groupe_student",
			joinColumns = { @JoinColumn(name = "groupe_id")  },
			inverseJoinColumns = { @JoinColumn(name = "student_id") })
	private List<Student> students = new ArrayList<>();
	@JsonIgnore
	@ManyToMany(mappedBy="allowedGroupes" , fetch = FetchType.LAZY)
	private List<LearningEvent> events = new ArrayList<>();
	private String acceptedStudents = "";
	
	

	public List<LearningEvent> getEvents() {
		return events;
	}
	public void setEvents(List<LearningEvent> events) {
		this.events = events;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAcceptedStudents() {
		return acceptedStudents;
	}
	public void setAcceptedStudents(String acceptedStudents) {
		this.acceptedStudents = acceptedStudents;
	}
	public List<Student> getStudents() {
		return students;
	}	
	public void setStudents(List<Student> students) {
		this.students = students;
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
	public Teacher getOwner() {
		return owner;
	}
	public void setOwner(Teacher owner) {
		this.owner = owner;
	}
	
	


}
