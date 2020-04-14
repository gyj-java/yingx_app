package com.gyj.yingx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    @Id
    private String id;

    private String title;

    private String brief;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "cover_img")
    private String coverImg;

    @Column(name = "publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "user_id")
    private String userId;

}