package com.cktv.domain;

public class Tpl {
    private long tpl_id;

    private String tpl_name;

    private String thumb_url;

    private long tpl_model;

    private long tpl_business;

    private String dynamic_url;

    private long user_id;

    private String duration;//播放时间

    private String tpl_size;

    private long is_video;

    private String tpl_address;

    private String edit_url;

    private String edit_address;
    private long tpl_x_coordinate;  //视频x坐标
    private long tpl_y_coordinate;
    private long tpl_width;
    private long tpl_height;
    private String video_source_url;  //视频地址

    public long getTpl_x_coordinate() {
        return tpl_x_coordinate;
    }

    public void setTpl_x_coordinate(long tpl_x_coordinate) {
        this.tpl_x_coordinate = tpl_x_coordinate;
    }

    public long getTpl_y_coordinate() {
        return tpl_y_coordinate;
    }

    public void setTpl_y_coordinate(long tpl_y_coordinate) {
        this.tpl_y_coordinate = tpl_y_coordinate;
    }

    public long getTpl_width() {
        return tpl_width;
    }

    public void setTpl_width(long tpl_width) {
        this.tpl_width = tpl_width;
    }

    public long getTpl_height() {
        return tpl_height;
    }

    public void setTpl_height(long tpl_height) {
        this.tpl_height = tpl_height;
    }

    public String getVideo_source_url() {
        return video_source_url;
    }

    public void setVideo_source_url(String video_source_url) {
        this.video_source_url = video_source_url;
    }

    public long getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(long tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String getTpl_name() {
        return tpl_name;
    }

    public void setTpl_name(String tpl_name) {
        this.tpl_name = tpl_name;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public long getTpl_model() {
        return tpl_model;
    }

    public void setTpl_model(long tpl_model) {
        this.tpl_model = tpl_model;
    }

    public long getTpl_business() {
        return tpl_business;
    }

    public void setTpl_business(long tpl_business) {
        this.tpl_business = tpl_business;
    }

    public String getDynamic_url() {
        return dynamic_url;
    }

    public void setDynamic_url(String dynamic_url) {
        this.dynamic_url = dynamic_url;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTpl_size() {
        return tpl_size;
    }

    public void setTpl_size(String tpl_size) {
        this.tpl_size = tpl_size;
    }

    public long getIs_video() {
        return is_video;
    }

    public void setIs_video(long is_video) {
        this.is_video = is_video;
    }

    public String getTpl_address() {
        return tpl_address;
    }

    public void setTpl_address(String tpl_address) {
        this.tpl_address = tpl_address;
    }

    public String getEdit_url() {
        return edit_url;
    }

    public void setEdit_url(String edit_url) {
        this.edit_url = edit_url;
    }

    public String getEdit_address() {
        return edit_address;
    }

    public void setEdit_address(String edit_address) {
        this.edit_address = edit_address;
    }

}
