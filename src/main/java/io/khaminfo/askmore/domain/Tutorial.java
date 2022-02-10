package io.khaminfo.askmore.domain;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;


import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Tutorial extends LearningEvent {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	private int delayComment;
	private int nbrComment;
	private int nbrVisitor;
	@OneToOne(mappedBy = "tutorial" ,fetch = FetchType.EAGER ,cascade=	CascadeType.ALL)
	private Questionnary questionnary; 
	
	
	
	
	
	public Questionnary getQuestionnary() {
		return questionnary;
	}
	public void setQuestionnary(Questionnary questionnary) {
		this.questionnary = questionnary;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public int getDelayComment() {
		return delayComment;
	}
	public void setDelayComment(int delayComment) {
		this.delayComment = delayComment;
	}
	public int getNbrComment() {
		return nbrComment;
	}
	public void setNbrComment(int nbrComment) {
		this.nbrComment = nbrComment;
	}
	public int getNbrVisitor() {
		return nbrVisitor;
	}
	public void setNbrVisitor(int nbrVisitor) {
		this.nbrVisitor = nbrVisitor;
	}
	
	
	@PrePersist
	protected void onCreate() {
		this.createAt = new Date();
	}
		

}
