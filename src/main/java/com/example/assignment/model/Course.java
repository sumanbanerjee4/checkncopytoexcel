package com.example.assignment.model;

import java.util.Date;
import java.util.List;

public class Course {
	
	
	private String courseExamId;	
	private String description;	
	private String type;	
	private Date dateOfFulfillment	;
	private String learningCredit;	
	private String isRelevant;	
	private String reasonIfNotRelevant;
	
	public Course() {}
	
	public String getCourseExamId() {
		return courseExamId;
	}
	public void setCourseExamId(String courseExamId) {
		this.courseExamId = courseExamId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public Date getDateOfFulfillment() {
		return dateOfFulfillment;
	}

	public void setDateOfFulfillment(Date dateOfFulfillment) {
		this.dateOfFulfillment = dateOfFulfillment;
	}

	public String getLearningCredit() {
		return learningCredit;
	}
	public void setLearningCredit(String learningCredit) {
		this.learningCredit = learningCredit;
	}
	public String getIsRelevant() {
		return isRelevant;
	}
	public void setIsRelevant(String isRelevant) {
		this.isRelevant = isRelevant;
	}
	public String getReasonIfNotRelevant() {
		return reasonIfNotRelevant;
	}
	public void setReasonIfNotRelevant(String reasonIfNotRelevant) {
		this.reasonIfNotRelevant = reasonIfNotRelevant;
	}
	public Course( String courseExamId, String description, String type, Date dateOfFulfillment,
			String learningCredit, String isRelevant, String reasonIfNotRelevant) {
		super();
		
		this.courseExamId = courseExamId;
		this.description = description;
		this.type = type;
		this.dateOfFulfillment = dateOfFulfillment;
		this.learningCredit = learningCredit;
		this.isRelevant = isRelevant;
		this.reasonIfNotRelevant = reasonIfNotRelevant;
	}
}