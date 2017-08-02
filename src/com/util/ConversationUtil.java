package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
	final static Logger log = Logger.getLogger(ConversationUtil.class);
	
	private static ConversationUtil instance = new ConversationUtil();
	
	private ConversationService service;
	
	private String workspaceId;
	
	private ConversationUtil() {
		String userName = PropertiesUtil.get("conversation.service.user.name");
		String password = PropertiesUtil.get("conversation.service.user.password");
		
		this.workspaceId = PropertiesUtil.get("conversation.workspace.id");
		
	    service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
	    service.setUsernameAndPassword(userName, password);
	}
	
	public static ConversationUtil getInstance() {
		return instance;
	}
	
	public MessageResponse sendMessage(String message) {
		return this.sendMessage(null, message);
	}
	
	/**
	 * 질의
	 * 
	 * @param context
	 * @param message
	 * @return
	 */
	public MessageResponse sendMessage(Map<String, Object> context, String message) {
		if(context == null) {
			context = new HashMap<String, Object>();
		}
		
		MessageRequest newMessage = new MessageRequest.Builder().context(context).inputText(message).build();
		 
		MessageResponse response = this.service.message(this.workspaceId, newMessage).execute();
		
		log.debug(response.toString());
		
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
		
		log.debug(response.toString());
		
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
		
		log.debug(response.toString());
		
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
		
		log.debug(response.toString());
		
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
	
	public static void main(String[] a) {
		ConversationUtil.getInstance().sendMessage("남아 있는지?");
	}
}
