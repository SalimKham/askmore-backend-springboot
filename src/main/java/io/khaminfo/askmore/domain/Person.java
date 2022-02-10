package io.khaminfo.askmore.domain;

import java.util.Collection;




import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;
	@Column(unique = true)
	@NotBlank(message = "username  is required.")
	private String username;
	@NotBlank(message = "Password is required")
	private String password;
	@Email(message = "Email needs to be in correct Format")
	@NotBlank(message = "Email is required")
	@Column(unique = true)
	private String email;
	private String confirmPassword;
	private int user_state;
	@JsonFormat(pattern = "yyyy-mm-dd")
	@Value(value = "2022-10-05")
	private Date block_date;
	@Value(value = "2")
	private int type;
	private int nbr_added;
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private Date last_Visit_date;

	@OneToOne(mappedBy = "user" ,fetch = FetchType.LAZY ,cascade=	 CascadeType.ALL, orphanRemoval = true)
	UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public int getUser_state() {
		return user_state;
	}

	public void setUser_state(int user_state) {
		this.user_state = user_state;
	}

	public Date getBlock_date() {
		return block_date;
	}

	public void setBlock_date(Date block_date) {
		this.block_date = block_date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNbr_added() {
		return nbr_added;
	}

	public void setNbr_added(int nbr_added) {
		this.nbr_added = nbr_added;
	}

	public Date getLast_Visit_date() {
		return last_Visit_date;
	}

	public void setLast_Visit_date(Date	 last_Visit_date) {
		this.last_Visit_date = last_Visit_date;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// userDetails interface
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

}
