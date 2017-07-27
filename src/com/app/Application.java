package com.app;

import java.util.List;

import com.bean.Utterance;
import com.parser.ExcelParser;

public class Application {

	public static void main(String[] args) throws Exception {
		
		ExcelParser parser = new ExcelParser();
		
		List<Utterance> utteranceList = parser.parse("d:\\test.xlsx");
		
		for(Utterance item : utteranceList) {
			//TODO call api
			
		}
	}
}
