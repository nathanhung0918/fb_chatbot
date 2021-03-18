package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReplyService {
//no param constructor
	public ReplyService() {
		
	}
	
//check which webhook it is
	@SuppressWarnings("unchecked")
	public String isWhichAction(HashMap<String,Object> page) {
		String action = "";
		if((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("messaging") != null)
			action = "message";
		//is a comment type
		else if (((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("changes")).get(0).get("value")).get("post")!=null) {
			//comment not from bot
			if(!((String)((Map<String, Object>)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("changes")).get(0).get("value")).get("from")).get("id")).equals("101302101761815"))
				//comment is the first floor
				if(((String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("changes")).get(0).get("value")).get("post_id")).equals( ((String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("changes")).get(0).get("value")).get("parent_id"))))
					action = "comment";
		}
		else
			action = "post";
		
		return action;
	}
	
//Create text data form to be send (in type of Map(Map(Map)))
	public HashMap<String,Object> createMessage(String psid,String text){
		HashMap<String,Object> request_body = new HashMap<String,Object>();
		HashMap<String,Object> recipient = new HashMap<String,Object>();
		HashMap<String,Object> message = new HashMap<String,Object>();
		recipient.put("id", psid);
		message.put("text",text);
		request_body.put("messaging_type", "RESPONSE");
		request_body.put("recipient", recipient);
		request_body.put("message", message);
		
		return request_body;
	}

//Create image data form to be send (in type of Map(Map(Map)))	
	public HashMap<String,Object> createImage(String psid,String link){
		HashMap<String,Object> request_body = new HashMap<String,Object>();
		HashMap<String,Object> message = new HashMap<String,Object>();
		HashMap<String,Object> payload = new HashMap<String,Object>();
		HashMap<String,Object> attachment = new HashMap<String,Object>();
		HashMap<String,Object> recipient = new HashMap<String,Object>();
		recipient.put("id", psid);
		payload.put("url", link);
		attachment.put("payload", payload);
		attachment.put("type", "image");
		message.put("attachment", attachment);
		request_body.put("message", message);
		request_body.put("recipient", recipient);
		
		return request_body;
	}
	
//return Comment data	(packaging)
	@SuppressWarnings("unchecked")
	public HashMap<String,Object> getCommentData(HashMap<String,Object> page) {
		HashMap<String,Object> mMap = new HashMap<String,Object>();
		String post_id = (String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("changes")).get(0).get("value")).get("comment_id");
		String text = "Good Comment!";
		mMap.put("mes", text);
		mMap.put("post_id", post_id);
	    return mMap;
	}
	
//return Message data   (packaging)
	@SuppressWarnings("unchecked")
	public HashMap<String,Object> getReplyData(HashMap<String,Object> page) {
		String psid = (String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("messaging")).get(0).get("sender")).get("id");
		String text = (String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("messaging")).get(0).get("message")).get("text");	
		return createMessage(psid,text);
	}
	
	
}






