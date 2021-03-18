package kafka.entity;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import config.MultiKafkaListenerConfig;

import org.springframework.kafka.support.KafkaHeaders;

@Component
public class KafkaConsumer {

//	@KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}",groupId = "#{'${kafka.groupId}'}")
//	public void listenGroupFoo(String message) {
//		setPayload(message);
//        latch.countDown();
//	    System.out.println("Received Message in group foo: " + message);
//	}

	//property can override consumer config except for group.id, client.id, use the groupId and clientIdPrefix annotation properties for those.
	@KafkaListener(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "#{'${kafka.groupId}'}", properties = {
			"max.poll.interval.ms:60000", ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=100" },clientIdPrefix = "")
	public void receive(ConsumerRecord<?, ?> consumerRecord) {
		setPayload(consumerRecord.toString());
		latch.countDown();
		System.out.println("Record: " + consumerRecord);
	}
	
	@MultiKafkaListenerConfig(topics = "#{'${spring.kafka.topics}'.split(',')}", groupId = "#{'${kafka.groupId}'}")
	public void testListener(ConsumerRecord<?, ?> consumerRecord){
		System.out.println("Record from test listener: " + consumerRecord);
	}
	

	private CountDownLatch latch = new CountDownLatch(1);
	private String payload = null;

	public CountDownLatch getLatch() {
		return latch;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public List<PartitionInfo> partitionsFor(String topic) {
		return partitionsFor(topic);

	}

}
