package app;

import java.util.List;

import com.bean.Utterance;
import com.parser.ExampleExcelParser;
import com.parser.ExcelParser;
import com.util.ConversationUtil;
import com.util.Logger;

/**
 * 
 * example 추가 프로그램
 *
 */
public class ExampleInsertApplication {

	public static void main(String[] args) throws Exception {
		
		Logger.debug("Start application.");
		
		ExampleInsertApplication app = new ExampleInsertApplication();
		
		/**
		 * conversation workspace intent에 example 추가
		 */
		app.createExampleData();
		
		//ConversationUtil.getInstance().createExample("Reservation", "하루묵어줍소!!!!!!.");
		
		//ConversationUtil.getInstance().sendMessage("방");
		
		
		Logger.debug("End application.");
	}
	
	/**
	 * conversation workspace의 intent에 example data 추가
	 * 
	 * 
	 * @return
	 */
	public boolean createExampleData() {
		Logger.debug("Start create example.");
		
		ExcelParser parser = new ExampleExcelParser();
		
		try {
			List<Utterance> utteranceList = parser.parseExcel();
			
			for(Utterance item : utteranceList) {
				//call api
				ConversationUtil.getInstance().createExample(item.getIntent(), item.getUtterance());
			}
		} catch (Exception e) {
			
			Logger.error("Training Failure.", e);
			
			return false;
		}
		
		Logger.debug("Success create example.");
		
		return true;
	}
}
