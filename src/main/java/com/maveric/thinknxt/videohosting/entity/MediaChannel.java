package com.maveric.thinknxt.videohosting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_channel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdDate;
}
