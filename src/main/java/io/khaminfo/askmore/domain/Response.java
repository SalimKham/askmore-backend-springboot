package io.khaminfo.askmore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Please Enter A  Valid String")
	@Column(columnDefinition = "TEXT")
	private String content;
	private int responseOrder;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", updatable = false, nullable = false)
	private Question question;
    private boolean valide = false;
    
    

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getResponseOrder() {
		return responseOrder;
	}

	public void setResponseOrder(int responseOrder) {
		this.responseOrder = responseOrder;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}



}
