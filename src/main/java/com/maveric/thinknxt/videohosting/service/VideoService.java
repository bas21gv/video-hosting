package com.maveric.thinknxt.videohosting.service;

import com.maveric.thinknxt.videohosting.dto.VideoMapper;
import com.maveric.thinknxt.videohosting.dto.VideoRequest;
import com.maveric.thinknxt.videohosting.dto.ViewCountRequest;
import com.maveric.thinknxt.videohosting.entity.MediaChannel;
import com.maveric.thinknxt.videohosting.entity.Video;
import com.maveric.thinknxt.videohosting.exception.ResourceNotFoundException;
import com.maveric.thinknxt.videohosting.processor.ChannelProcessor;
import com.maveric.thinknxt.videohosting.repository.ChannelRepository;
import com.maveric.thinknxt.videohosting.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final ChannelProcessor channelProcessor;

    public void addVideo(VideoRequest videoRequest) {
        Video video = videoRepository.save(VideoMapper.mapToVideo(videoRequest));
        channelProcessor.addVideo(videoRequest.getUploadedTo(), video);
    }

    @Transactional
    public void updateViewCount(ViewCountRequest viewCountRequest) {
        videoRepository.updateViewCountByVideoIdAndChannelId(viewCountRequest.getViewCount(),
                viewCountRequest.getVideoId(), viewCountRequest.getChannelId());
        Video video = videoRepository.findById(viewCountRequest.getVideoId())
                .orElseThrow(()->new ResourceNotFoundException("Video not exists by givenId: "+viewCountRequest.getVideoId()));
        channelProcessor.addVideo(viewCountRequest.getVideoId(), video);
    }
}
