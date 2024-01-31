package com.maveric.thinknxt.videohosting;

import com.maveric.thinknxt.videohosting.dto.NewVideosNotification;
import com.maveric.thinknxt.videohosting.dto.SubscriberResponse;
import com.maveric.thinknxt.videohosting.dto.SubscriptionNotification;
import com.maveric.thinknxt.videohosting.entity.Video;
import com.maveric.thinknxt.videohosting.serdes.NewVideosNotificationSerdes;
import com.maveric.thinknxt.videohosting.serdes.SubscriberResponseSerdes;
import com.maveric.thinknxt.videohosting.serdes.SubscriptionNotificationSerdes;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(info =
@Info(title = "Video Hosting API", version = "2.3.0", description = "Documentation Customer API v1.0")
)
@Slf4j
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Bean
    public Serde<SubscriberResponse> subscriberResponseSerdes() {
        return new SubscriberResponseSerdes();
    }

    @Bean
    public Serde<SubscriptionNotification> subscriptionNotificationSerdes() {
        return new SubscriptionNotificationSerdes();
    }

    @Bean
    public Serde<NewVideosNotification> newVideosNotificationSerdes() {
        return new NewVideosNotificationSerdes();
    }

    @Bean
    public Consumer<KStream<Long, SubscriberResponse>> retrieve() {
        return (inputStream) -> inputStream.peek((key,value)->log.info("retrieve key: {} value: {}",key,value));
    }

    /**
     *  Grouping by key channel id
     *  windowing for 3 mins and extra 30 secs grace period,
     *  aggregating all subscribers in SubscriberNotification for same key,
     *  using materialized store with LongSerde for key, SubscriptionNotificationSerdes for value
     *  converting to stream
     *  Using map as the stream has windowdkey and suscriptionNotification as balue
     *  Using keyvaluemapper which maps windowedkey,SubscriptionNotification value stream to channelid,SubscriptionNotification value stream
     *  returning the finally mapped sstream
     *
     */
    @Bean
    public Function<KStream<Long, SubscriberResponse>, KStream<Long, SubscriptionNotification>> subscribeNotification() {
        KeyValueMapper<Windowed<Long>,SubscriptionNotification, KeyValue<Long,SubscriptionNotification>> keyValueMap =(windowKey, value)->new KeyValue<>(windowKey.key(),value);
        Function<KStream<Long, SubscriberResponse>, KStream<Long, SubscriptionNotification>> function = inputStream -> {
            KStream<Long, SubscriptionNotification> grouped = inputStream
                    .peek((key,value)->log.info("Before delivery:: channelid: {} subsciberInfo {}",key,value))
                    .groupByKey()
                    .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1),Duration.ofSeconds(30)))
                    .aggregate(SubscriptionNotification::new,
                            (key,value,aggV)-> {
                                log.info("inside window :: key:: {} value:: {} aggv:: {} ",key,value,aggV);
                                if(key == aggV.getChannelId()) {
                                    aggV.getSubscriberInfos().add(value);
                                } else {
                                    aggV.setChannelId(key);
                                    Set<SubscriberResponse> subscriberInfo = new HashSet<>();
                                    subscriberInfo.add(value);
                                    aggV.setSubscriberInfos(subscriberInfo);
                                }
                                return aggV;
                            },
                            Materialized.with(Serdes.Long(),subscriptionNotificationSerdes()))
                    .toStream()
                    .map(keyValueMap)
                    .peek((key,value)->log.info("After delivery:: channelid: {} subscriptionNotification {}",key,value));
            return grouped;
        };
        return function;
    }


    @Bean
    public Function<KStream<Long, Video>, KStream<Long, Integer>> videoView() {
        Function<KStream<Long, Video>, KStream<Long, Integer>> function = inputStream -> {
            KStream<Long, Integer> viewCount = inputStream
                    .peek((key,value)->log.info("Before delivery:: videoid: {} video {}",key,value))
                    .mapValues(Video::getViewCount)
                    .peek((key,value)->log.info("After delivery:: videoid: {} viewcount {}",key,value));
            return viewCount;
        };
        return function;
    }
}
