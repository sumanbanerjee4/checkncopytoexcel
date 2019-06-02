package com.example.assignment.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import com.example.assignment.model.Employees;

@Component
public class EmployeeRepoImpl {
	
	@Autowired
	EmployeeRepo r;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	public EmployeeRepoImpl(MongoTemplate mongoTemplate){
		this.mongoTemplate=mongoTemplate;
	}
	
	public List<Employees> findCourseCompletionDetails(){
		
		List<AggregationOperation> list = new ArrayList<>();
		
		list.add(Aggregation.match(Criteria.where("course.dateOfFulfillment").is(null)));
		TypedAggregation<Employees> agg = Aggregation.newAggregation(Employees.class, list);
		
		return mongoTemplate.aggregate(agg, Employees.class, Employees.class).getMappedResults();
}}

