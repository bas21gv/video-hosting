package com.maveric.thinknxt.videohosting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCountRequest {
    private Long videoId;
    private Long channelId;
    private Integer viewCount;
}
