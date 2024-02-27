package com.maveric.thinknxt.videohosting.dto;

import com.maveric.thinknxt.videohosting.entity.Subscriber;

public class SubscriberMapper {

    public static Subscriber mapToSubscriber(SubscriberRequest subscriberRequest) {
        return Subscriber.builder()
                .firstName(subscriberRequest.getFirstName())
                .lastName(subscriberRequest.getLastName())
                .email(subscriberRequest.getEmail())
                .build();
    }

    public static SubscriberInfo mapToSubscriberInfo(Subscriber subscriber) {
        return SubscriberInfo.builder()
                .id(subscriber.getId())
                .firstName(subscriber.getFirstName())
                .lastName(subscriber.getLastName())
                .email(subscriber.getEmail())
                .build();
    }
}
