package com.maveric.thinknxt.videohosting.service;

import com.maveric.thinknxt.videohosting.dto.VideoMapper;
import com.maveric.thinknxt.videohosting.dto.VideoRequest;
import com.maveric.thinknxt.videohosting.dto.VideoViewRequest;
import com.maveric.thinknxt.videohosting.entity.Video;
import com.maveric.thinknxt.videohosting.exception.ResourceNotFoundException;
import com.maveric.thinknxt.videohosting.processor.ChannelProcessor;
import com.maveric.thinknxt.videohosting.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final ChannelProcessor channelProcessor;

    public void addVideo(VideoRequest videoRequest) {
        Video video = videoRepository.save(VideoMapper.mapToVideo(videoRequest));
        channelProcessor.addVideo(videoRequest.getUploadedTo(), video);
    }

    public void updateVideoView(VideoViewRequest videoViewRequest) {
        Video video = videoRepository.findById(videoViewRequest.getVideoId())
                .orElseThrow(()->new ResourceNotFoundException("Video not exists by givenId: "+ videoViewRequest.getVideoId()));
        video.setViewCount(video.getViewCount()+1);
        Video updatedVideo = videoRepository.save(video);
        channelProcessor.videoView(videoViewRequest.getSubscriberId(), updatedVideo);
    }
}
