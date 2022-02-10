package io.khaminfo.askmore.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Name is required!")
	private String name;
	private String description;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="field_id", updatable = false, nullable = false)
   
	private Field field;
	

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
