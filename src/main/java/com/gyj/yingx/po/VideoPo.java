package com.gyj.yingx.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author guoyunjie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoPo implements Serializable, Cloneable {


    private String id;
    private String vTitle;
    private String vBrief;
    private String vPath;
    private String vCover;
    private Date vPublishDate;

    private String likeCount;
    private String playCount;
    private String isAttention;

    private String cateName;
    private String categoryId;

    private String userId;
    private String headImg;
    private String userName;

    private List<VideoPo> videoList;

    @Override
    protected Object clone() {
        VideoPo videoPo = null;
        try {
            videoPo = (VideoPo) super.clone();
            // person.arrFavor = arrFavor.clone();
            return videoPo;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
