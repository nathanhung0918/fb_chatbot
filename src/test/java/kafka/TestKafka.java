package kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.example.demo.FbChatBotApplication;

import kafka.entity.KafkaConsumer;
import kafka.entity.KafkaProducer;

@SpringBootTest(classes = {FbChatBotApplication.class})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9094", "port=9094" })
class TestKafka {

	@Autowired
	private KafkaConsumer consumer;

	@Autowired
	private KafkaProducer producer;

	@Test
	public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived() throws Exception {
		producer.send("webhook", "Sending with own simple KafkaProducer");
		consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
		assertEquals(consumer.getLatch().getCount(), 0L);
		assertTrue(consumer.getPayload().contains("Sending with own simple KafkaProducer"));
	}
}