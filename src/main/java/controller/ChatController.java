package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import kafka.entity.KafkaProducer;

//Restful Controller

@RestController
public class ChatController {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	KafkaProducer kafkaProducer;
	@Value("${spring.kafka.topics}")
	private String[] topics;


//access token
	final String myToken = "EAASuO60rRYEBAHjo9u7TFLYSxgMQ4wZARby1ryQ5SfMfm8nUBXkhTN0sZCqAwO7xqZC4GoHri2X9v58bmZApqWn64RsVTfXwEPZBssigbFd9EMLLUKsuAasYuiuOOeKIa4H4t56bJhchWcEEqzkdg2f1ZCCJKrkDuBpC9Qt9TZBXQZDZD";

//Used to build connection to the FB server
	@GetMapping("/webhook")
	@ResponseBody
	public ResponseEntity<String> getInfo(@RequestParam("hub.verify_token") String verify_token,
			@RequestParam("hub.challenge") String challenge, @RequestParam("hub.mode") String mode) {
		// ensure getting data form request and response ok
		if (!mode.isEmpty() && !verify_token.isEmpty()) {
			if (mode.equals("subscribe") && verify_token.equals(myToken))
				return new ResponseEntity<String>(challenge, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

//Used to received data from FB server
	@SuppressWarnings("unchecked")
	@PostMapping("/webhook")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> postInfo(@RequestBody HashMap<String, Object> page) {
		String text = (String)((Map<String, Object>)((ArrayList<Map<String,Object>>)((Map<String,Object>)((ArrayList<Map<String,Object>>)page.get("entry")).get(0)).get("messaging")).get(0).get("message")).get("text");	

		kafkaProducer.send("testrep",text);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
