package com.example.assignment.repo;



import java.io.File;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.assignment.model.Course;
import com.example.assignment.model.Employees;
import com.mongodb.DuplicateKeyException;

//import io.micrometer.core.instrument.MultiGauge.Row;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static java.util.Comparator.comparing;


@Component
public class ExcelToObject {
	private double empno;

	
	@Autowired
	EmployeeRepo employeeRepo;

	
	
	MongoTemplate mongoTemplate;

	@Autowired
	public ExcelToObject(MongoTemplate mongoTemplate){
		this.mongoTemplate=mongoTemplate;
	}

	DataFormatter dataFormatter = new DataFormatter();

	private List<Course> courseList;

	public Boolean createObject(String fileName){
		Boolean save =true;
		try{
			FileInputStream file = new FileInputStream(new File("C://Temp//" +fileName));

			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			List <Employees> employeelist = new ArrayList<>();

			for (int i= sheet.getFirstRowNum()+ 5; i<=sheet.getLastRowNum();i++){
				Employees e=new Employees();

				Row ro =sheet.getRow(i);

				for (int j= ro.getFirstCellNum(); j<= ro.getLastCellNum();j++ ){

					Cell ce= ro.getCell(j);

					if(j==0){
						ce.getNumericCellValue();
						e.setEmpno(ce.getNumericCellValue());
					}


					if (j==1){
						e.setEmpName(ce.getStringCellValue());
					}
					if (j==2){
						e.setProjectId(ce.getNumericCellValue());
					}
					if (j==3){
						e.setProjectDescr(ce.getStringCellValue());
					}
					if (j==4){
						e.setIbu(ce.getStringCellValue());
					}
					if (j==5){
						e.setIbg(ce.getStringCellValue());
					}
					if (j==6){
						e.setCluster(ce.getStringCellValue());
					}
					if (j==7){
						e.setCustomer(ce.getStringCellValue());
					}
					if (j==8){
						e.setLocation(ce.getStringCellValue());
					}
					if (j==9){
						e.setEmpFunction(ce.getStringCellValue());
					}
					if (j==10){
						e.setEmpSubFunction(ce.getStringCellValue());
					}
					if (j==11){
						e.setBand(ce.getStringCellValue());
					}
				}

				employeelist.add(e);
			}

			List<Employees> unique = employeelist.stream().collect(collectingAndThen(

					toCollection(()->new TreeSet<>(comparing(Employees:: getEmpno))),ArrayList::new));
			try{
				employeeRepo.saveAll(unique);
				System.out.println("added at source");
			}catch(DuplicateKeyException ex){
				System.out.println("already in database, not inserted");
				save=false;
			}
			file.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return save;
	}

	public void addCourse(String fileName){
		List<Employees> u=employeeRepo.findAll();
		for(Employees emp:u){
			if(emp.getCourse()!= null){
				courseList=emp.getCourse();
			}
			else if(emp.getCourse()==null){
				courseList= new ArrayList<Course>();
			}
			try{
				FileInputStream file = new FileInputStream(new File("C://Temp//" +fileName));

				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);



				for (int i= sheet.getFirstRowNum()+ 5; i<=sheet.getLastRowNum();i++){
					

					Row ro =sheet.getRow(i);
					short firstCell = ro.getFirstCellNum();
					empno = ro.getCell(firstCell).getNumericCellValue();
					if(empno==emp.getEmpno()){
						Course course=new Course();


						for (int j= firstCell; j<= ro.getLastCellNum();j++ ){

							Cell ce= ro.getCell(j);




							if (j==12 && emp.getEmpno()==empno){
								course.setCourseExamId(ce.getStringCellValue());
							}
							if (j==13  && emp.getEmpno()==empno){
								course.setDescription(ce.getStringCellValue());
							}
							if (j==14  && emp.getEmpno()==empno){
								course.setType(ce.getStringCellValue());
							}
							if (j==15  && emp.getEmpno()==empno){
								course.setDateOfFulfillment(ce.getDateCellValue());
							}
							if (j==16  && emp.getEmpno()==empno){
								course.setLearningCredit(Double.toString(ce.getNumericCellValue()));
							}
							if (j==17  && emp.getEmpno()==empno){
								course.setIsRelevant(ce.getStringCellValue());
							}
							if (j==18  && emp.getEmpno()==empno){
								course.setReasonIfNotRelevant(ce.getStringCellValue());
							}


						}

						courseList.add(course);
					}
					}
				Query select=Query.query(Criteria.where("empno").is(emp.getEmpno()));
				Update update = new Update();
				update.set("course", courseList);
				mongoTemplate.findAndModify(select, update, Employees.class);
				file.close();
			}
			catch(Exception e){
				e.printStackTrace();

			}}}}



//				final Aggregation agg=Aggregation.newAggregation(
//						Aggregation.match(Criteria.where("empno").is(372569)),
//						Aggregation.project("empno",
//								              "empName",
//								              "project_id",
//								              "project_descr",
//								              "ibu",
//								              "ibg",
//								              "cluster",
//								              "customer",
//								              "location",
//								              "emp_function",
//								              "emp_sub_function").and("band").project("course_exam_id", "description",
//								            		  "type","date_of_fulfillment","learning_credit","is_relevant",
//								            		  "reason_if_not_relevant").as("course"),
//						Aggregation.group("empno").push("course").as("courses"),
//						Aggregation.project("variantDetails.sku","variantDetails.barcode") );
//				final AggregationResult<Product> result =this.mongoTemplate.aggregate
//						(agg, this.mongoTemplate.getCollectionName(Product.class),Product.class);
//				return result.getMappedResults();
//			}
//			file.close();
//			}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//
//}
//
