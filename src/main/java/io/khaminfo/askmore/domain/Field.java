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

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Field {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Name is required.")
	@Column(unique = true)
	private String name;
	@NotBlank(message = "Description is reqired")
	private String descr;
	private boolean is_arrchived  = false;
	@OneToMany(mappedBy = "field", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	@JsonIgnore
    private List<Subject> subjects = new ArrayList<>();
	
	
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public boolean isIs_arrchived() {
		return is_arrchived;
	}
	public void setIs_arrchived(boolean is_arrchived) {
		this.is_arrchived = is_arrchived;
	}
	

}
