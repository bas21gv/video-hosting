package com.maveric.thinknxt.videohosting.repository;

import com.maveric.thinknxt.videohosting.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Modifying
    @Query("update Video v set v.viewCount= :viewCount where v.id= :videoId and v.uploadedTo= :channelId")
    void updateViewCountByVideoIdAndChannelId(@Param("viewCount") Integer viewCount,
                                              @Param("videoId") Long videoId,
                                              @Param("channelId") Long channelId);
}
