package com.maveric.thinknxt.videohosting.serdes;

import com.maveric.thinknxt.videohosting.dto.SubscriberResponse;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class SubscriberResponseSerdes implements Serde<SubscriberResponse> {
    @Override
    public Serializer<SubscriberResponse> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<SubscriberResponse> deserializer() {
        return new JsonDeserializer<>(SubscriberResponse.class);
    }
}
