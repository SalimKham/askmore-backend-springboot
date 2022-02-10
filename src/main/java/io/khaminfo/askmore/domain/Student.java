package io.khaminfo.askmore.domain;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student extends Person{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonIgnore
	@ManyToMany(mappedBy="students" , fetch = FetchType.EAGER)
	private List<Groupe> groupes = new ArrayList<>();

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}
	public String getGroupesString() {
		String groupes="";
		for (Groupe groupe : this.groupes) {
			groupes+=groupe.getId()+"/";
		}
		return groupes;
	}

}
