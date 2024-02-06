package com.maveric.thinknxt.videohosting.repository;

import com.maveric.thinknxt.videohosting.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
