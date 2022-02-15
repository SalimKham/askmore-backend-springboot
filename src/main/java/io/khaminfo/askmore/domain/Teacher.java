package io.khaminfo.askmore.domain;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Teacher extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cv;

	private String joinedGroupes = "";


	


	public String getCv() {
		return cv;
	}



	public void setCv(String cv) {
		this.cv = cv;
	}

	public String getJoinedGroupes() {
		return joinedGroupes;
	}

	public void setJoinedGroupes(String joinedGroupes) {
		this.joinedGroupes = joinedGroupes;
	}

}
