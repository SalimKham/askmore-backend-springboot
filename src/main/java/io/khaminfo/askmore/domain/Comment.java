package io.khaminfo.askmore.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Comment {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private long idReplay;
@NotBlank(message = "Content required.")
private String content;
@JsonFormat(pattern = "yyyy-MM-dd")
private Date createdAt;
@ManyToOne(cascade = CascadeType.REFRESH)
@JoinColumn(name="user_id", updatable = false, nullable = false)
Person user;
@ManyToOne(cascade = CascadeType.REFRESH)
@JoinColumn(name="parent_id", updatable = false, nullable = false)
LearningEvent Parent;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public long getIdReplay() {
	return idReplay;
}
public void setIdReplay(long idReplay) {
	this.idReplay = idReplay;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Person getUser() {
	return user;
}
public void setUser(Person user) {
	this.user = user;
}
public LearningEvent getParent() {
	return Parent;
}
public void setParent(LearningEvent parent) {
	Parent = parent;
}


@PrePersist
protected void onCreate() {
	this.createdAt = new Date();
}


}
