package io.khaminfo.askmore.domain;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int mark;
	@NotBlank(message = "Please Enter A valid String.")
	@Column(columnDefinition = "TEXT")
	private String Question;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionnnary_id", updatable = false, nullable = false)
	private Questionnary questinnary;
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<Response> responses = new ArrayList<>();
	
	


	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = question;
	}

	public Questionnary getQuestinnary() {
		return questinnary;
	}

	public void setQuestinnary(Questionnary questinnary) {
		this.questinnary = questinnary;
	}

}
