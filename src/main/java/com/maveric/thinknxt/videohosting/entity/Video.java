package com.maveric.thinknxt.videohosting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    private Integer widthResolution;
    private Integer heightResolution;
    private Long uploadedTo;
    private LocalDateTime uploadDateTime;
    private int viewCount;
    private String description;
}
