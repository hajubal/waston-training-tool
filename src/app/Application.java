package app;

import java.util.List;

import com.bean.Utterance;
import com.parser.ExcelParser;
import com.util.Logger;
import com.util.MessageSendUtil;

/**
 * 
 * 실행 프로그램
 *
 */
public class Application {

	public static void main(String[] args) throws Exception {
		
		Logger.debug("Start application.");
		
		Application app = new Application();
		
		boolean isSuccess = app.training();
		
		if(isSuccess) {
			app.test();
		}
		
		Logger.debug("End application.");
	}
	
	/**
	 * excel data를 이용한 training
	 * 
	 * @return
	 */
	public boolean training() {
		Logger.debug("Start training.");
		
		ExcelParser parser = new ExcelParser();
		
		try {
			List<Utterance> utteranceList = parser.parseUtterance();
			
			for(Utterance item : utteranceList) {
				//call api
				MessageSendUtil.getInstance().createExample(item.getIntent(), item.getUtterance());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			Logger.error("Training Failure.");
			
			
			return false;
		}
		
		Logger.debug("Success training.");
		
		return true;
	}
	
	/**
	 * test set을 이용하여 테스트.
	 * score 저장
	 * @throws Exception
	 */
	public void test() throws Exception {
		Logger.debug("Start test.");
		
		
		
		//TODO test data excel load
		
		//TODO loop: message send and result save
		
		
		Logger.debug("Success test.");
	}
}
