package com.util;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classifier;

public class NLCUtil {
	final static Logger log = Logger.getLogger(NLCUtil.class);
	
	private static String NAME_PREFIX = "Training_";
	
	private static NLCUtil instance = new NLCUtil();

	private NaturalLanguageClassifier service = new NaturalLanguageClassifier();
	
	private NLCUtil() {
		this.service.setUsernameAndPassword(PropertiesUtil.get("nlc.service.username"), PropertiesUtil.get("nlc.service.password"));
		
//		this.service.getClassifier(classifierId).enqueue(new ServiceCallback() {
//
//			@Override
//			public void onFailure(Exception arg0) {
//				//System.out.println(arg0);
//			}
//
//			@Override
//			public void onResponse(Object arg0) {
//				//System.out.println(arg0);
//			}
//		});
	}
	
	public static NLCUtil getInstance() {
		return instance;
	}

	/**
	 * api call
	 * 
	 * 제일 마지막에 만들어진 classifier 호출
	 * 
	 * @param message
	 * @return 
	 */
	public Classification sendMessage(String message) {
		return this.sendMessage(null, message);
	}
	
	/**
	 * api call
	 * 
	 * @param classifierId
	 * @param message
	 * @return
	 */
	public Classification sendMessage(String classifierId, String message) {
		if(classifierId == null) {
			Classifier classifier = this.getLastClassifier();
			
			if(classifier != null) {
				classifierId = this.getLastClassifier().getId();
			}
		}
		
		Classification classification = this.service.classify(classifierId, message).execute();
		
		log.debug(classification.toString());
		
		return classification;
	}
	
	/**
	 * getClassifier
	 * 
	 * @param classifierId
	 * @return
	 */
	public Classifier getClassifier(String classifierId) {
		
		Classifier classifier = this.service.getClassifier(classifierId).execute();
		
		log.debug(classifier.toString());
		
		return classifier;
	}
	
	/**
	 * training
	 * 
	 * @return
	 */
	public Classifier training() {
		String filePath = PropertiesUtil.get("trainingdata.filepath");
		
		//training csv file
		File file = new File(filePath);
		
		return this.training(file);
	}
	
	/**
	 * 새로운 classifier를 생성하여 training. 
	 * 
	 * @param file
	 * @return
	 */
	public Classifier training(File file) {
		
		if(file.exists() == false) {
			log.error("Training data is null");
			return null;
		}
		
		Classifier classifier = this.service.createClassifier(this.generateName(), "ko", file).execute();
		
		log.debug(classifier.toString());
		
		return classifier;
	}
	
	/**
	 * 마지막에 생성된 classifier 이름
	 * 
	 * @return
	 */
	public String getLastClassifierName() {
		
		return this.getLastClassifier().getName();
	}
	
	/**
	 * 마지막에 생성된 classifier
	 * 
	 * @return
	 */
	public Classifier getLastClassifier() {
		List<Classifier> list = this.getAllClassifier();
		
		if(list == null) {
			return null;
		}
		
		list.sort(new Comparator<Classifier>() {
			@Override
			public int compare(Classifier o1, Classifier o2) {
				return o1.getCreated().compareTo(o2.getCreated());
			}
		});
		
		return list.get(list.size()-1);
	}
	
	/**
	 * getLastClassifierName
	 * 
	 * @return
	 */
	public List<Classifier> getAllClassifier() {
		List<Classifier> list = this.service.getClassifiers().execute().getClassifiers();
		
		log.debug(list.toString());
		
		return list; 
	}
	
	/**
	 * get NaturalLanguageClassifier
	 * 
	 * @return
	 */
	public NaturalLanguageClassifier getService() {
		return this.service;
	}
	
	private String generateName() {
		String lastName = this.getLastClassifierName();
		
		if(lastName == null || lastName.indexOf("_") < 0 ) {
			lastName = "1";
		} else {
			lastName = lastName.substring(lastName.indexOf("_")+1, lastName.length());
			
			int num = Integer.parseInt(lastName);
			
			num = num + 1;
			
			lastName = num + "";
		}
		
		return NAME_PREFIX + lastName;
	}
	
	
	public static void main(String[] a) {
		//Classifier cls = NLCUtil.getInstance().training(new File("C:\\Users\\hajubal\\Desktop\\test.csv"));
		
		//Classification cls = NLCUtil.getInstance().sendMessage("쉬고싶은데");
		

//		Classification response = NLCUtil.getInstance().sendMessage("room");
//		JsonParser parser = new JsonParser();
//		JsonObject object = parser.parse(response.toString()).getAsJsonObject();
//		
//		Database database = DBUtil.getInstance().getDatabase();
//		database.save(object);
		
	}
}
