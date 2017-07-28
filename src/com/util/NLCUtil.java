package com.util;

import com.ibm.watson.developer_cloud.http.ServiceCallback;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

public class NLCUtil {
	private static String userName = "6e210c1a-561c-4d85-924b-fcff71f6263f";
	private static String password = "6bMlp2zj0ZSX";
	private static String classifierId = "359f3fx202-nlc-237745";
	
	private static NLCUtil instance = new NLCUtil();

	private NaturalLanguageClassifier service = new NaturalLanguageClassifier();
	
	private NLCUtil() {
		this.service.setUsernameAndPassword(userName, password);
		
		this.service.getClassifier(classifierId).enqueue(new ServiceCallback() {

			@Override
			public void onFailure(Exception arg0) {
				//System.out.println(arg0);
				
			}

			@Override
			public void onResponse(Object arg0) {
				//System.out.println(arg0);
				
			}
		});
	}
	
	public static NLCUtil getInstance() {
		return instance;
	}

	/**
	 * api call
	 * 
	 * @param message
	 * @return 
	 */
	public Classification sendMessage(String message) {
		Classification classification = this.service.classify(classifierId, message).execute();
		
		return classification;
	}
	
	public void training() {
		
	}
}
