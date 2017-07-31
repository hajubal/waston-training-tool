package app;

import java.util.List;

import com.bean.Utterance;
import com.parser.ExcelParser;
import com.util.Logger;
import com.util.ConversationUtil;

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
		
		ExcelParser parser = new ExcelParser();
		
		try {
			List<Utterance> utteranceList = parser.parseUtterance();
			
			for(Utterance item : utteranceList) {
				//call api
				ConversationUtil.getInstance().createExample(item.getIntent(), item.getUtterance());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			Logger.error("Training Failure.");
			
			
			return false;
		}
		
		Logger.debug("Success create example.");
		
		return true;
	}
}
