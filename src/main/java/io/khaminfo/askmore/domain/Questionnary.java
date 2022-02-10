package io.khaminfo.askmore.domain;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Questionnary  {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
@JsonIgnore
@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JoinColumn(unique = true, name = "id_tutorial")
private Tutorial tutorial;
@OneToMany(mappedBy = "questinnary", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
private List<Question> questions = new ArrayList<>();


public List<Question> getQuestions() {
	return questions;
}
public void setQuestions(List<Question> questions) {
	this.questions = questions;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public Tutorial getTutorial() {
	return tutorial;
}
public void setTutorial(Tutorial tutorial) {
	this.tutorial = tutorial;
}



}
