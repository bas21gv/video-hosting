package com.maveric.thinknxt.videohosting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {
    private String name;
    private String title;
    private Integer widthResolution;
    private Integer heightResolution;
    private Long uploadedTo;
    private LocalDateTime uploadDateTime;
    private String description;
}
