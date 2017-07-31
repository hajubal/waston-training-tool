package app;

import com.util.Logger;

/**
 * 
 * 프레이닝 프로그램
 *
 */
public class TrainingApplication {

	public static void main(String[] args) throws Exception {
		
		Logger.debug("Start application.");
		
		TrainingApplication app = new TrainingApplication();
		
		/**
		 * NLC 서비스에 대화 트레이닝
		 */
		boolean isSuccess = app.training();
		
		/**
		 * NLC 서비스 테스트
		 */
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
