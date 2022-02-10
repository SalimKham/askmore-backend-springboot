package io.khaminfo.askmore.domain;

import java.util.Date;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_Info;
	@Value(value = " ")
	private String firstName;
	@Value(value = " ")
	private String lastName;
	@Value(value = " ")
	private String address;
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Value(value = "1991-08-05")
	private Date birthday;
	@Value(value = "0")
	private int birthday_type;
	private int rand =  new Random().nextInt(10,100);
	@Value(value = " ")
	private String photo = "https://i.pravatar.cc/50?img="+ rand +" https://i.pravatar.cc/50?img="+rand;
	@Value(value = "M")
	private char sex;
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(unique = true, name = "id_user")
	private Person user;

	
	
	
	public int getBirthday_type() {
		return birthday_type;
	}

	public void setBirthday_type(int birthday_type) {
		this.birthday_type = birthday_type;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public long getId_Info() {
		return id_Info;
	}

	public void setId_Info(long id_Info) {
		this.id_Info = id_Info;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}



	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

}
