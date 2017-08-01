/**
 * 
 */
package com.parser;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;

import com.bean.Utterance;
import com.util.PropertiesUtil;

/**
 * test excel file parsing 
 */
public class TestExcelParser extends ExcelParser {

	@Override
	protected Utterance getUtterance(int rowIndex, int cellIndex, Cell cell) {
		//TODO create utterance object, list
		
		return null;
	}

	@Override
	protected File getFile() {
		File file = new File(PropertiesUtil.get("testdata.filepath"));
		
		return file;
	}
}
