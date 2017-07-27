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
	 * system.properties에 지정된 파일 파싱
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parse() throws Exception {
		return this.parse(PropertiesUtil.get("tool.filepath"));
	}
	
	/**
	 * 지정된 경로의 파일 파싱
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parse(String filePath) throws Exception {
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


	private boolean isExist(String filePath) {
		if(filePath == null) {
			return false;
		}
		
		File file = new File(filePath);
		
		return file.exists();
	}
}
