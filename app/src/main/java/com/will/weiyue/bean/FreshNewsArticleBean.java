package com.will.weiyue.bean;

import java.io.Serializable;

/**
 * Created by android on 2018/1/22.
 */

public class FreshNewsArticleBean implements Serializable {

    private String status;
    private PostBean post;
    private String previous_url;

    private String title;
    private String excerpt;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PostBean getPost() {
        return post;
    }

    public void setPost(PostBean post) {
        this.post = post;
    }

    public String getPrevious_url() {
        return previous_url;
    }

    public void setPrevious_url(String previous_url) {
        this.previous_url = previous_url;
    }


    public static class PostBean {

        private int id;
        private String content;
        private String date;
        private String modified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }
    }
}
