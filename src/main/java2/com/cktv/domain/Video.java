package com.cktv.domain;

import java.util.Date;

/**
 * Created by hws on 16/6/24.
 */
public class Video {
    private int video_id;
    private String video_url;
    private String video_name;

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public String getVideo_thumb_url() {
        return video_thumb_url;
    }

    public void setVideo_thumb_url(String video_thumb_url) {
        this.video_thumb_url = video_thumb_url;
    }

    public String getVideo_time_length() {
        return video_time_length;
    }

    public void setVideo_time_length(String video_time_length) {
        this.video_time_length = video_time_length;
    }

    public String getVideo_url() {
        return video_url;

    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    private Date upload_time;
    private String video_thumb_url;
    private String video_time_length;

}
