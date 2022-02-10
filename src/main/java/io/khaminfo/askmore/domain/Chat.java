package io.khaminfo.askmore.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity 
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
	private Date sendAt;
	@Column(columnDefinition = "TEXT")
	private String content;
	private long sender;
	private long receiver;
	private boolean vue = false;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getSendAt() {
		return sendAt;
	}
	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getSender() {
		return sender;
	}
	public void setSender(long sender) {
		this.sender = sender;
	}
	public long getReceiver() {
		return receiver;
	}
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	public boolean isVue() {
		return vue;
	}
	public void setVue(boolean vue) {
		this.vue = vue;
	}
	@PrePersist
	protected void onCreate() {
		this.sendAt = new Date();
	}

}
