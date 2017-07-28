package com.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.bean.TestUtterance;
import com.bean.Utterance;
import com.util.Logger;
import com.util.PropertiesUtil;

/**
 * 대화뭉치 excel 파일 파서
 * 
 */
public class ExcelParser {

	public ExcelParser() {
		
	}
	
	/**
	 * system.properties에 지정된 training 데이터 파일 파싱
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parseUtterance() throws Exception {
		return this.parseUtterance(PropertiesUtil.get("trainingdata.filepath"));
	}
	
	/**
	 * 지정된 경로의 테스트 training 파일 파싱
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parseUtterance(String filePath) throws Exception {
		List<Utterance> utteranceList = new ArrayList<Utterance>();
		
		if(this.isExist(filePath) == false)  {
			Logger.debug("Excel file not found : " + filePath);
			
			return utteranceList;
		}

		Workbook wb = WorkbookFactory.create(new File(filePath));

		Iterator<Sheet> sheets = wb.iterator();
		
		while(sheets.hasNext()) {
			Sheet sheet = sheets.next();
			Row row = null;
			
			Iterator<Row> rows = sheet.iterator();
			Iterator<Cell> cells = null;
			Cell cell = null;
			
			int cellIdx = 0;
			int rowIdx = 0;
			
			while(rows.hasNext()) {
				row = rows.next();
				
				cells = row.cellIterator();
				
				cellIdx = 0;
				rowIdx++;
				
				while(cells.hasNext()) {
					cell = cells.next();
					
					Logger.debug("row_" + rowIdx + ", cell_" + cellIdx++ + ": " + cell.getNumericCellValue());
					
					//TODO create utterance object, list
				}
			}
		}
		
		return utteranceList;
	}
	
	/**
	 * system.properties에 지정된 test 데이터 파일 파싱
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TestUtterance> parseTest() throws Exception {
		return this.parseTest(PropertiesUtil.get("testdata.filepath"));
	}
	
	/**
	 * 지정된 경로의 테스트 데이터 파일 파싱
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<TestUtterance> parseTest(String filePath) throws Exception {
		List<TestUtterance> utteranceList = new ArrayList<TestUtterance>();
		
		if(this.isExist(filePath) == false)  {
			Logger.debug("Excel file not found : " + filePath);
			
			return utteranceList;
		}

		Workbook wb = WorkbookFactory.create(new File(filePath));

		Iterator<Sheet> sheets = wb.iterator();
		
		while(sheets.hasNext()) {
			Sheet sheet = sheets.next();
			Row row = null;
			
			Iterator<Row> rows = sheet.iterator();
			Iterator<Cell> cells = null;
			Cell cell = null;
			
			int cellIdx = 0;
			int rowIdx = 0;
			
			while(rows.hasNext()) {
				row = rows.next();
				
				cells = row.cellIterator();
				
				cellIdx = 0;
				rowIdx++;
				
				while(cells.hasNext()) {
					cell = cells.next();
					
					Logger.debug("row_" + rowIdx + ", cell_" + cellIdx++ + ": " + cell.getNumericCellValue());
					
					//TODO create test object, list
				}
			}
		}
		
		return utteranceList;
	}


	private boolean isExist(String filePath) {
		if(filePath == null) {
			return false;
		}
		
		File file = new File(filePath);
		
		return file.exists();
	}
}
