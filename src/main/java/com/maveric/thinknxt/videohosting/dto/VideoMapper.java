package com.maveric.thinknxt.videohosting.dto;

import com.maveric.thinknxt.videohosting.entity.Video;

import java.time.LocalDateTime;

public class VideoMapper {

    public static Video mapToVideo(VideoRequest videoRequest) {
        return Video.builder()
                .name(videoRequest.getName())
                .title(videoRequest.getTitle())
                .description(videoRequest.getDescription())
                .widthResolution(videoRequest.getWidthResolution())
                .heightResolution(videoRequest.getHeightResolution())
                .uploadedTo(videoRequest.getUploadedTo())
                .uploadDateTime(LocalDateTime.now())
                .build();
    }

    public static VideoResponse mapToVideoResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .name(video.getName())
                .title(video.getTitle())
                .description(video.getDescription())
                .widthResolution(video.getWidthResolution())
                .heightResolution(video.getHeightResolution())
                .uploadedTo(video.getUploadedTo())
                .uploadDateTime(video.getUploadDateTime())
                .viewCount(video.getViewCount())
                .build();
    }
}
