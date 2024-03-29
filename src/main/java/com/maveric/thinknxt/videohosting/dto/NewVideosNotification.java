package com.maveric.thinknxt.videohosting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewVideosNotification {
    private SubscriberInfo subscriberInfo;
    private Set<VideoResponse> videoInfos;
}
