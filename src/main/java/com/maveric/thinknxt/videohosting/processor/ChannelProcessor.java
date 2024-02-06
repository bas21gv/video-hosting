package com.maveric.thinknxt.videohosting.processor;

import com.maveric.thinknxt.videohosting.dto.VideoMapper;
import com.maveric.thinknxt.videohosting.dto.VideoResponse;
import com.maveric.thinknxt.videohosting.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelProcessor {

    private static final String NEWVIDEO_FUNCTION = "newvideo-out-0";
    private static final String VIDEOVIEW_FUNCTION = "videoview-out-0";
    private final StreamBridge streamBridge;

    public void addVideo(Long channelId, Video video) {
        VideoResponse videoResponse = VideoMapper.mapToVideoResponse(video);
        Message<VideoResponse> message = MessageBuilder.withPayload(videoResponse)
                .setHeader(KafkaHeaders.KEY, channelId)
                .build();
        streamBridge.send(NEWVIDEO_FUNCTION,message);
    }

    public void videoView(Long subscriberId, Video video) {
        VideoResponse videoResponse = VideoMapper.mapToVideoResponse(video);
        Message<VideoResponse> message = MessageBuilder.withPayload(videoResponse)
                .setHeader(KafkaHeaders.KEY, subscriberId)
                .build();
        streamBridge.send(VIDEOVIEW_FUNCTION,message);
    }
}
