package com.students.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.students.bean.StudentBean;
import com.students.entity.StudentEntity;
import com.students.repository.StudentRepository;

@Service
public class StudentHelper {
	
	@Autowired
	private StudentRepository studentrepository;
	
	@Autowired
	private StudentEntity studententity;
	
    //check that file is of excel type or not

	public static boolean checkExcelFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
	}
    
	//convert excel to list of products

    public List<StudentEntity> convertExcelToListOfProduct(InputStream is, HttpServletResponse response) {
    	List<StudentEntity> list = new ArrayList<>();
        List<String> sheetHeaders=new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();

        try {
        	XSSFWorkbook workbook = new XSSFWorkbook(is);
        	
        	XSSFSheet sheet = workbook.getSheet("Sheet1");
        
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (nextRow.getRowNum() == 0) {
                	sheetHeaders = getExcellRows(nextRow);
                } else {
                    rows.add(getExcellRows(nextRow));
            }
        }
            updateExcelCell(sheet, 0, 4).setCellValue("STATUS");
            updateExcelCell(sheet, 0, 5).setCellValue("REASON");
// row read
            int rowNumber = 0;
            Iterator<Row> iterator1 = sheet.iterator();

            while (iterator1.hasNext()) {
                Row row = iterator1.next();

                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
              //cell read
                Iterator<Cell> cells = row.iterator();

                int cid = 0;

                StudentEntity s = new StudentEntity();

                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                    case 0:
                    	s.setStudentId(cell.getStringCellValue());
                    	break;
					default:
                    		break;

                }
                    cid++;
                }
              //check and update price
                for (List<String> row1 : rows) {
                    //  Object productDtoObj = Class.forName("beans.ProductPriceBean").newInstance();

                    StudentBean studentbean = getStudentBean((List<String>) sheetHeaders, new StudentBean(), row1);
                    int studententity = studentrepository.updateStudentCourse(studentbean.getCourse(), studentbean.getStudentId());

                  
                    if(studententity==1) {
                    	
//                    	studententity =new StudentEntity();
//                    	studententity.setStudentId(studentbean.getStudentId());
//                    	studententity.setCourse(studentbean.getCourse());
//                    	studententity.setStudentName(studentbean.getStudentName());
//                    	studentrepository.save(studententity);
                    	
                        updateExcel(sheet, rowNumber, "updated Successfully", "Success");

                        
                    } else {
                        updateExcel(sheet, rowNumber, "ID existed", "Failed");
                    }

                    rowNumber++;

                }

                writeResponse(workbook, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    	
    }

      private StudentBean getStudentBean(List<String> sheetHeaders, StudentBean studentBean, List<String> row1) {
		// TODO Auto-generated method stub
		return null;
	}

	//check last row's cell num
        public List<String> getExcellRows(Row nextRow) {
            List<String> rows = new ArrayList<>();

            for (int i = 0; i < nextRow.getLastCellNum(); i++) {

                Cell cel = nextRow.getCell(i);
                if (cel == null) {
                    rows.add("NA");
                } else {
                    switch (cel.getCellTypeEnum()) {   //getcelltype check num data type and convert

                        case BOOLEAN:
                            rows.add(cel.getBooleanCellValue() + "");
                            break;

                        case NUMERIC:
                            rows.add(cel.getNumericCellValue() + "");
                            break;

                        case STRING:
                            rows.add(cel.getStringCellValue());
                            break;

                        case BLANK:
                            rows.add("NA");
                            break;

                        default:
                    }
                }

            }

            return rows;
        }
        
        
        private void updateExcel(Sheet firstSheet, int rowCount, String reason, String status) {

            try {
                updateExcelCell(firstSheet, rowCount, 5).setCellValue(status);

                updateExcelCell(firstSheet, rowCount, 6).setCellValue(reason);
            } catch (Exception e) {

            }
        }
        
        
        private Cell updateExcelCell(Sheet firstSheet, int rowCount, int statusCellIndex) {
            return firstSheet.getRow(rowCount).createCell(statusCellIndex);
        }
        
        
        private void writeResponse(Workbook workbook, HttpServletResponse response) throws IOException {
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        }
}
