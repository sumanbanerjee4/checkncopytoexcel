package com.example.assignment.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.assignment.model.Employees;

public interface EmployeeRepo extends MongoRepository<Employees, Double>{
	
	
	@Query(value="{'empno':?0}",fields="{'course': 1}")
	public Employees findAllCourseofEmployee(double userName);
	
	@Query(value="{'course.dateOfFulfillment':'null'}",fields="{'empno': 1, 'course':1}")
	public List<Employees>  findCourseCompletionDetails();
	
	//public Optional<Employee> findByEmpno(String empno); 

	
	
	/*@Query("{'$and':[{'name':?0},{'Device.name': ?1}]}")
	public  User findAllContactsByDevice(String userName,String deviceName);*/
}