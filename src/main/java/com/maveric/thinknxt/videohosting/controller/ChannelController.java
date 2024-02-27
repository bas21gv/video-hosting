package com.maveric.thinknxt.videohosting.controller;

import com.maveric.thinknxt.videohosting.dto.MediaChannelRequest;
import com.maveric.thinknxt.videohosting.dto.VideoRequest;
import com.maveric.thinknxt.videohosting.dto.VideoViewRequest;
import com.maveric.thinknxt.videohosting.entity.MediaChannel;
import com.maveric.thinknxt.videohosting.service.ChannelService;
import com.maveric.thinknxt.videohosting.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channel")
public class ChannelController {

    private final ChannelService channelService;
    private final VideoService videoService;
    @PostMapping
    public ResponseEntity<MediaChannel> addMediaChannel(@RequestBody MediaChannelRequest mediaChannelRequest) {
        return new ResponseEntity<>(channelService.addMediaChannel(mediaChannelRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaChannel> getMediaChannel(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.getMediaChannel(id));
    }

    @PostMapping("/video")
    public ResponseEntity<String> addVideo(@RequestBody VideoRequest videoRequest) {
        videoService.addVideo(videoRequest);
        return ResponseEntity.ok("Video added and message sent!");
    }

    @PutMapping("/video/view")
    public ResponseEntity<String> updateVideoView(@RequestBody VideoViewRequest videoViewRequest) {
        videoService.updateVideoView(videoViewRequest);
        return ResponseEntity.ok("Video viewcount updated!!");
    }

}
