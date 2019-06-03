package com.example.assignment.model;

import java.util.List;



import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

@Document
@JsonIgnoreType				
public class Employees {
	
	@Indexed(unique=true)
	private double empno;	
	private String empName;	
	private double projectId;	
	private String projectDescr;	
	private String ibu	;
	private String ibg;
	private String cluster;	
	private String customer;	
	private String location;	
	private String empFunction;	
	private String empSubFunction;	
	private String band;
	
	
	public Employees (){}
	
	private List<Course> course;


	public double getEmpno() {
		return empno;
	}

	public void setEmpno(double empno) {
		this.empno = empno;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getProjectId() {
		return projectId;
	}

	public void setProjectId(double projectId) {
		this.projectId = projectId;
	}

	public String getProjectDescr() {
		return projectDescr;
	}

	public void setProjectDescr(String projectDescr) {
		this.projectDescr = projectDescr;
	}

	public String getIbu() {
		return ibu;
	}

	public void setIbu(String ibu) {
		this.ibu = ibu;
	}

	public String getIbg() {
		return ibg;
	}

	public void setIbg(String ibg) {
		this.ibg = ibg;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmpFunction() {
		return empFunction;
	}

	public void setEmpFunction(String empFunction) {
		this.empFunction = empFunction;
	}

	public String getEmpSubFunction() {
		return empSubFunction;
	}

	public void setEmpSubFunction(String empSubFunction) {
		this.empSubFunction = empSubFunction;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public Employees(double empno, String empname, double projectId, String projectDescr, String ibu, String ibg,
			String cluster, String customer, String location, String empFunction, String empSubFunction, String band,
			List<Course> course) {
		super();
		this.empno = empno;
		this.empName = empname;
		this.projectId = projectId;
		this.projectDescr = projectDescr;
		this.ibu = ibu;
		this.ibg = ibg;
		this.cluster = cluster;
		this.customer = customer;
		this.location = location;
		this.empFunction = empFunction;
		this.empSubFunction = empSubFunction;
		this.band = band;
		this.course = course;
	} 
	
	
}
