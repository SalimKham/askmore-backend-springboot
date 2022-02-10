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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class LearningEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message ="title required")
	private String title;
	@NotBlank(message = "Content requried")
	@Column(columnDefinition = "TEXT")
	private String content;
	private int contentType;
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="subject_id", updatable = false, nullable = false)
	private Subject subject;
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="teacher_id", updatable = false, nullable = false)
	private Teacher teacher;

	@OneToMany(mappedBy = "Parent", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "groupe_event",
			joinColumns = { @JoinColumn(name = "event_id")  },
			inverseJoinColumns = { @JoinColumn(name = "groupe_id") })
	
	private List<Groupe> allowedGroupes = new ArrayList<>();
	
	
	public List<Groupe> getAllowedGroupes() {
		return allowedGroupes;
	}
	public void setAllowedGroupes(List<Groupe> allowedGroupes) {
		this.allowedGroupes = allowedGroupes;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public long getId() {
		
		
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getContentType() {
		return contentType;
	}
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
