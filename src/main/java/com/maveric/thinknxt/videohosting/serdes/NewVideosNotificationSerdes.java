package com.maveric.thinknxt.videohosting.serdes;

import com.maveric.thinknxt.videohosting.dto.NewVideosNotification;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class NewVideosNotificationSerdes implements Serde<NewVideosNotification> {
    @Override
    public Serializer<NewVideosNotification> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<NewVideosNotification> deserializer() {
        return new JsonDeserializer<>(NewVideosNotification.class);
    }
}
