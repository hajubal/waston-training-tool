package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.CreateExample;
import com.ibm.watson.developer_cloud.conversation.v1.model.CreateExample.Builder;
import com.ibm.watson.developer_cloud.conversation.v1.model.ExampleResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.IntentResponse;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

/**
 * conversation api call util
 * 
 *
 */
public class ConversationUtil {
	
	private static ConversationUtil instance = new ConversationUtil();
	
	private ConversationService service;
	
	private String workspaceId;
	
	private ConversationUtil() {
		String userName = PropertiesUtil.get("service.user.name");
		String password = PropertiesUtil.get("service.user.password");
		
		this.workspaceId = PropertiesUtil.get("workspace.id");
		
	    service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
	    service.setUsernameAndPassword(userName, password);
	}
	
	public static ConversationUtil getInstance() {
		return instance;
	}
	
	/**
	 * 질의
	 * 
	 * @param context
	 * @param message
	 * @return
	 */
	public MessageResponse sendMessage(Map<String, Object> context, String message) {
		MessageRequest newMessage = new MessageRequest.Builder().context(context).inputText(message).build();
		 
		MessageResponse response = this.service.message(this.workspaceId, newMessage).execute();
		
		return response;
	}
	
	/**
	 * intent 생성
	 * 
	 * @param intent
	 * @param description
	 * @param examples
	 * @return
	 */
	public IntentResponse createIntent(String intent, String description, List<String> examples) {
		if(Asserter.isNullOrEmpty(intent) || Asserter.isNullOrEmpty(examples)) {
			return null;
		}
		
		List<CreateExample> list = new ArrayList<CreateExample>();
		
		for(String str : examples) {
			list.add( new Builder().text(str).build());
		}
		
		IntentResponse response = this.service.createIntent(this.workspaceId, intent, description, list).execute();
		
		return response;
	}
	
	/**
	 * intent 수정
	 * 
	 * @param intent 수정 할 intent 이름
	 * @param newIntent 변경 될 intent 이름
	 * @param newDescription 변경 될 description
	 * @param examples 예제
	 * @return
	 */
	public IntentResponse updateIntent(String intent, String newIntent, String newDescription, List<String> examples) {
		if(Asserter.isNullOrEmpty(intent) || Asserter.isNullOrEmpty(examples)) {
			return null;
		}
		
		List<CreateExample> list = new ArrayList<CreateExample>();
		
		for(String str : examples) {
			list.add( new Builder().text(str).build());
		}
		
		IntentResponse response = this.service.updateIntent(this.workspaceId, intent, newIntent, newDescription, list).execute();
		
		return response;
	}
	
	/**
	 * 말뭉치 추가
	 * 
	 * @param intent
	 * @param exampleText
	 * @return
	 */
	public ExampleResponse createExample(String intent, String exampleText) {
		
		ExampleResponse response = this.service.createExample(this.workspaceId, intent, exampleText).execute(); 
		
		return response;
	}
	
	/**
	 * conversation 객체
	 * 
	 * @return
	 */
	public ConversationService getConversationService() {
		return this.service;
	}
}
