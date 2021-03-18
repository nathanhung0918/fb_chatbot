package config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener
public @interface MultiKafkaListenerConfig {

    @AliasFor(annotation = KafkaListener.class, attribute = "groupId")
    String groupId();

    @AliasFor(annotation = KafkaListener.class, attribute = "topics")
    String[] topics();

    @AliasFor(annotation = KafkaListener.class, attribute = "concurrency")
    String concurrency() default "3";

}