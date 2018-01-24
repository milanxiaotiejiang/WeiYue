package com.will.weiyue.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2018/1/16.
 */

public class NewsDetail implements Serializable {

    @Override
    public String toString() {
        return "NewsDetail{" +
                "listId='" + listId + '\'' +
                ", type='" + type + '\'' +
                ", expiredTime=" + expiredTime +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", topsize=" + topsize +
                ", item=" + item +
                '}';
    }

    private String listId;
    private String type;
    private int expiredTime;
    private int currentPage;
    private int totalPage;
    private int topsize;
    private List<ItemBean> item;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTopsize() {
        return topsize;
    }

    public void setTopsize(int topsize) {
        this.topsize = topsize;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean implements Serializable, MultiItemEntity {

        //广告类型
        public static final int TYPE_ADVERT_TITLEIMG = 1;

        public static final int TYPE_ADVERT_SLIDEIMG = 2;

        public static final int TYPE_ADVERT_LONGIMG = 3;
        //图片类型
        public static final int TYPE_SLIDE = 4;
        //视频类型
        public static final int TYPE_PHVIDEO = 5;

        //显示形式单图
        public static final int TYPE_DOC_TITLEIMG = 6;
        //显示形式多图
        public static final int TYPE_DOC_SLIDEIMG = 7;

        @Override
        public String toString() {
            return "ItemBean{" +
                    "type='" + type + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", online='" + online + '\'' +
                    ", title='" + title + '\'' +
                    ", showType='" + showType + '\'' +
                    ", source='" + source + '\'' +
                    ", subscribe=" + subscribe +
                    ", updateTime='" + updateTime + '\'' +
                    ", id='" + id + '\'' +
                    ", documentId='" + documentId + '\'' +
                    ", staticId='" + staticId + '\'' +
                    ", style=" + style +
                    ", commentsUrl='" + commentsUrl + '\'' +
                    ", comments='" + comments + '\'' +
                    ", commentsall='" + commentsall + '\'' +
                    ", link=" + link +
                    ", simId='" + simId + '\'' +
                    ", reftype='" + reftype + '\'' +
                    ", recomToken='" + recomToken + '\'' +
                    ", hasSlide=" + hasSlide +
                    '}';
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public int itemType;
        private String type;
        private String thumbnail;
        private String online;
        private String title;
        private String showType;
        private String source;
        private SubscribeBean subscribe;
        private String updateTime;
        private String id;
        private String documentId;
        private String staticId;
        private StyleBean style;
        private String commentsUrl;
        private String comments;
        private String commentsall;
        private LinkBean link;
        private String simId;
        private String reftype;
        private String recomToken;
        private boolean hasSlide;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public SubscribeBean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(SubscribeBean subscribe) {
            this.subscribe = subscribe;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getStaticId() {
            return staticId;
        }

        public void setStaticId(String staticId) {
            this.staticId = staticId;
        }

        public StyleBean getStyle() {
            return style;
        }

        public void setStyle(StyleBean style) {
            this.style = style;
        }

        public String getCommentsUrl() {
            return commentsUrl;
        }

        public void setCommentsUrl(String commentsUrl) {
            this.commentsUrl = commentsUrl;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getCommentsall() {
            return commentsall;
        }

        public void setCommentsall(String commentsall) {
            this.commentsall = commentsall;
        }

        public LinkBean getLink() {
            return link;
        }

        public void setLink(LinkBean link) {
            this.link = link;
        }

        public String getSimId() {
            return simId;
        }

        public void setSimId(String simId) {
            this.simId = simId;
        }

        public String getReftype() {
            return reftype;
        }

        public void setReftype(String reftype) {
            this.reftype = reftype;
        }

        public String getRecomToken() {
            return recomToken;
        }

        public void setRecomToken(String recomToken) {
            this.recomToken = recomToken;
        }

        public boolean isHasSlide() {
            return hasSlide;
        }

        public void setHasSlide(boolean hasSlide) {
            this.hasSlide = hasSlide;
        }

        public static class SubscribeBean {
            @Override
            public String toString() {
                return "SubscribeBean{" +
                        "cateid='" + cateid + '\'' +
                        ", type='" + type + '\'' +
                        ", catename='" + catename + '\'' +
                        ", description='" + description + '\'' +
                        '}';
            }

            private String cateid;
            private String type;
            private String catename;
            private String description;

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class StyleBean {
            @Override
            public String toString() {
                return "StyleBean{" +
                        "view='" + view + '\'' +
                        ", backreason=" + backreason +
                        ", images=" + images +
                        '}';
            }

            private String view;
            private List<String> backreason;
            private List<String> images;

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }

            public String getView() {
                return view;
            }

            public void setView(String view) {
                this.view = view;
            }

            public List<String> getBackreason() {
                return backreason;
            }

            public void setBackreason(List<String> backreason) {
                this.backreason = backreason;
            }
        }

        public static class LinkBean {

            private String type;
            private String url;
            private String weburl;

            @Override
            public String toString() {
                return "LinkBean{" +
                        "type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", weburl='" + weburl + '\'' +
                        '}';
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }
        }
    }
}
