package app;

import java.util.List;

import org.apache.log4j.Logger;

import com.bean.Utterance;
import com.parser.ExampleExcelParser;
import com.parser.ExcelParser;
import com.util.ConversationUtil;

/**
 * 
 * example 추가 프로그램
 *
 */
public class ExampleInsertApplication {
	
	final static Logger log = Logger.getLogger(ExampleInsertApplication.class);

	public static void main(String[] args) throws Exception {
		
		log.debug("Start application.");
		
		ExampleInsertApplication app = new ExampleInsertApplication();
		
		/**
		 * conversation workspace intent에 example 추가
		 */
		app.createExampleData();
		
		log.debug("End application.");
	}
	
	/**
	 * conversation workspace의 intent에 example data 추가
	 * 
	 * 
	 * @return
	 */
	public boolean createExampleData() {
		log.debug("Start create example.");
		
		ExcelParser parser = new ExampleExcelParser();
		
		try {
			List<Utterance> utteranceList = parser.parseExcel();
			
			for(Utterance item : utteranceList) {
				//call api
				ConversationUtil.getInstance().createExample(item.getIntent(), item.getUtterance());
			}
		} catch (Exception e) {
			
			log.error("Training Failure.", e);
			
			return false;
		}
		
		log.debug("Success create example.");
		
		return true;
	}
}
