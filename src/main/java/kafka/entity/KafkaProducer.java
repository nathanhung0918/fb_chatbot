package kafka.entity;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public ListenableFuture<SendResult<String, Object>> send(String topic, String data) {
		//callback to check if send success
//		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,data);
//	    future.addCallback(null, new ListenableFutureCallback<SendResult<Integer, String>>() {
//
//	        @Override
//	        public void onSuccess(SendResult<Integer, String> result) {
//	            //handleSuccess
//	        }
//
//	        @Override
//	        public void onFailure(Throwable ex) {
//	            //handleFailure
//	        }
//
//	    });
		return kafkaTemplate.send(topic, data);
	}
	
	public ListenableFuture<SendResult<String, Object>> send(String topic, String key, String data) {
		return kafkaTemplate.send(topic, key, data);
	}

	public ListenableFuture<SendResult<String, Object>> send(String topic, Integer partition, String key, Object data) {
		return kafkaTemplate.send(topic, partition, key, data);
	}
	
	public ListenableFuture<SendResult<String, Object>> send(String topic, Integer partition, Long timestamp,
			String key, Object data) {
		return kafkaTemplate.send(topic, partition, timestamp, key, data);
	}
	
	public ListenableFuture<SendResult<String, Object>> send(ProducerRecord<String, Object> record) {
		return kafkaTemplate.send(record);
	}
	
	public ListenableFuture<SendResult<String, Object>> send(Message<String> message) {
		return kafkaTemplate.send(message);
	}


	

}
