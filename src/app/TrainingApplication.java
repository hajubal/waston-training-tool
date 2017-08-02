package app;

import org.apache.log4j.Logger;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifier.Status;
import com.util.NLCUtil;

/**
 * 
 * 프레이닝 프로그램
 *
 */
public class TrainingApplication {
	
	final static Logger log = Logger.getLogger(TrainingApplication.class);

	public static void main(String[] args) throws Exception {
		
		log.debug("Start application.");
		
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
		
		log.debug("End application.");
	}
	
	
	/**
	 * excel data를 이용한 training
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	public boolean training() throws InterruptedException {
		log.debug("Start training.");
		
		Classifier classifier = NLCUtil.getInstance().training();
		
		if(classifier == null) {
			log.error("Fail to training.");
			return false;
		}
		
		String classifierId = classifier.getId();
		
		boolean isEndTraining = false;
		
		while(isEndTraining == false) {
			
			if(Status.AVAILABLE.equals(NLCUtil.getInstance().getClassifier(classifierId).getStatus())) {
				isEndTraining = true;
			}
			
			log.debug("Waiting for training.....");
			
			Thread.sleep(1000 * 30 * 1); //30 sec
		}
		
		log.debug("Success training.");
		
		return true;
	}
	
	/**
	 * test set을 이용하여 테스트.
	 * score 저장
	 * @throws Exception
	 */
	public void test() throws Exception {
		log.debug("Start test.");
		
		//TODO test data excel load
		
		

		//TODO loop: message send and result save
//		Classification response = NLCUtil.getInstance().sendMessage("room");
//		JsonParser parser = new JsonParser();
//		JsonObject object = parser.parse(response.toString()).getAsJsonObject();
//		
//		Database database = DBUtil.getInstance().getDatabase();
//		database.save(object);
		
		
		log.debug("Success test.");
	}
	
	
}
