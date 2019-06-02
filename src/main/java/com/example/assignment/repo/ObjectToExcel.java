package com.example.assignment.repo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
//import static org.apache.poi.ss.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.example.assignment.model.Employees;

@Component
public class ObjectToExcel {
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	EmployeeRepoImpl employeeRepoImpl;
	@Autowired
	MongoTemplate mongoTemplate;


	private List<String> id;

	private List<String> reason;

	private Cell splitCell;


	public String exportToFile() {

		String[] columns = { "Empno", "EmpName", "ProjectId", "ListOfCourse/Reason" };

		XSSFWorkbook workbook = new XSSFWorkbook();
		CreationHelper createHelper = workbook.getCreationHelper();

		XSSFSheet sheet = workbook.createSheet("Employee Details");

		List<Employees> list = employeeRepoImpl.findCourseCompletionDetails();

		Font headerFont = workbook.createFont();
		 headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.RED.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int i = 0; i < columns.length; i++) {

			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));

		int rowNum = 1;

		for (Employees employee : list) {

			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(employee.getEmpno());
			row.createCell(1).setCellValue(employee.getEmpName());
			row.createCell(2).setCellValue(employee.getProjectId());

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setColor(IndexedColors.BRIGHT_GREEN.getIndex());

			style.setFont(font);
			CellStyle style1 = workbook.createCellStyle();

			Font font1 = workbook.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			style.setFont(font);

			id = employee.getCourse().stream().map(i -> i.getCourseExamId()).collect(Collectors.toList());
			reason = employee.getCourse().stream().map(i -> i.getReasonIfNotRelevant()).collect(Collectors.toList());
			
			Map<String, String> map = IntStream.range(0, id.size()).collect(HashMap::new,
					(m, i) -> m.put(id.get(i), reason.get(i)), Map::putAll

			);

			Cell cell = row.createCell(3);
			cell.setCellValue(map.entrySet().stream().map(entry -> entry.getKey() + "-" + entry.getValue())
					.collect(Collectors.joining(",")));
			cell.setCellStyle(style);
			row.setHeight((short) 1100);
			sheet.setColumnWidth(0, 9500);
		}

		for (int i = 0; i < columns.length; i++) {

			sheet.autoSizeColumn(i);
		}

		try {
			FileOutputStream out = new FileOutputStream(new File("C://Temp//Employee.xlsx"));
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "written successfully in disk";
	}
}
