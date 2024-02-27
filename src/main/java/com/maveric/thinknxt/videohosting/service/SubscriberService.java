package com.maveric.thinknxt.videohosting.service;

import com.maveric.thinknxt.videohosting.dto.SubscribeChannelRequest;
import com.maveric.thinknxt.videohosting.dto.SubscriberInfo;
import com.maveric.thinknxt.videohosting.dto.SubscriberMapper;
import com.maveric.thinknxt.videohosting.dto.SubscriberRequest;
import com.maveric.thinknxt.videohosting.entity.MediaChannel;
import com.maveric.thinknxt.videohosting.entity.Subscriber;
import com.maveric.thinknxt.videohosting.exception.ResourceNotFoundException;
import com.maveric.thinknxt.videohosting.processor.SubscriberProcessor;
import com.maveric.thinknxt.videohosting.repository.ChannelRepository;
import com.maveric.thinknxt.videohosting.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final ChannelRepository channelRepository;
    private final SubscriberRepository subscriberRepository;
    private final SubscriberProcessor subscriberProcessor;

    public SubscriberInfo registerSubscriber(SubscriberRequest subscriberRequest) {
        Subscriber subscriber = SubscriberMapper.mapToSubscriber(subscriberRequest);
        return SubscriberMapper.mapToSubscriberInfo(subscriberRepository.save(subscriber));
    }

    public void createSubscriber(SubscribeChannelRequest subscribeChannelRequest) {
        Subscriber subscriber = subscriberRepository.findByEmail(subscribeChannelRequest.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("Subscriber not found with given name: "+subscribeChannelRequest.getEmail()));
        MediaChannel mediaChannel = channelRepository.findByName(subscribeChannelRequest.getSubscribeChannel())
                .orElseThrow(()-> new ResourceNotFoundException("Channel not exists with given name :"+subscribeChannelRequest.getSubscribeChannel()));
        SubscriberInfo subscriberInfo = SubscriberMapper.mapToSubscriberInfo(subscriber);
        subscriberProcessor.subcribeChannel(mediaChannel.getId(), subscriberInfo);
    }
}
