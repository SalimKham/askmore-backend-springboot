package io.khaminfo.askmore.domain;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Teacher extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cv;
	@JsonIgnore
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<Groupe> groupes = new ArrayList<>();

	public String getCv() {
		return cv;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

}
