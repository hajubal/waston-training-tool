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

/**
 * excel 파일 파서
 * 
 */
public abstract class ExcelParser {

	public ExcelParser() {
		
	}
	
	/**
	 * system.properties에 지정된 example 데이터 파일 파싱
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parseExcel() throws Exception {
		return this.parseExcel(this.getFile());
	}
	
	/**
	 * 지정된 경로의 테스트 exmaple 파일 파싱
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<Utterance> parseExcel(File file) throws Exception {
		List<Utterance> utteranceList = new ArrayList<Utterance>();
		
		if(file == null || file.exists() == false)  {
			Logger.debug("Excel file not found.");
			
			return utteranceList;
		}

		Workbook wb = WorkbookFactory.create(file);

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
					
					Logger.debug("row_" + rowIdx + ", cell_" + cellIdx + ": " + cell.getNumericCellValue());
					
					utteranceList.add(this.getUtterance(rowIdx, cellIdx, cell));
					
					cellIdx++;
				}
			}
		}
		
		return utteranceList;
	}
	
	protected abstract Utterance getUtterance(int rowIndex, int cellIndex, Cell cell);
	
	protected abstract File getFile();
}
