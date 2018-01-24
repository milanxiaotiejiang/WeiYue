package com.will.weiyue.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2018/1/17.
 */

public class NewsArticleBean implements Serializable {

    @Override
    public String toString() {
        return "NewsArticleBean{" +
                "meta=" + meta +
                ", body=" + body +
                ", disclaimer='" + disclaimer + '\'' +
                '}';
    }

    private MetaBean meta;
    private BodyBean body;
    private String disclaimer;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }


    public static class MetaBean {
        private String id;
        private String type;
        private String documentId;
        @SerializedName("class")
        private String classX;
        private int o;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public int getO() {
            return o;
        }

        public void setO(int o) {
            this.o = o;
        }

    }

    public static class BodyBean {
        private String aid;
        private String staticId;
        private String id;
        private String documentId;
        private String title;
        private String author;
        private String editorcode;
        private String source;
        private String thumbnail;
        private String editTime;
        private String updateTime;
        private String cTime;
        private String uTime;
        private String wwwurl;
        private String shareurl;
        private String commentsUrl;
        private String text;
        private int commentCount;
        private String commentType;
        private String allowComment;
        private SubscribeBean subscribe;
        private List<SlidesBean> slides;
        private String praise;
        private String hasEditor;
        private String hasVideo;
        private List<ImgBean> img;
        private List<RelateDocsBean> relateDocs;

        public String getEditorcode() {
            return editorcode;
        }

        public void setEditorcode(String editorcode) {
            this.editorcode = editorcode;
        }

        public List<SlidesBean> getSlides() {
            return slides;
        }

        public void setSlides(List<SlidesBean> slides) {
            this.slides = slides;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getStaticId() {
            return staticId;
        }

        public void setStaticId(String staticId) {
            this.staticId = staticId;
        }

        public String getDocumentId() {
            return documentId;
        }

        public void setDocumentId(String documentId) {
            this.documentId = documentId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCTime() {
            return cTime;
        }

        public void setCTime(String cTime) {
            this.cTime = cTime;
        }

        public String getUTime() {
            return uTime;
        }

        public void setUTime(String uTime) {
            this.uTime = uTime;
        }

        public String getWwwurl() {
            return wwwurl;
        }

        public void setWwwurl(String wwwurl) {
            this.wwwurl = wwwurl;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getCommentsUrl() {
            return commentsUrl;
        }

        public void setCommentsUrl(String commentsUrl) {
            this.commentsUrl = commentsUrl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getCommentType() {
            return commentType;
        }

        public void setCommentType(String commentType) {
            this.commentType = commentType;
        }

        public String getAllowComment() {
            return allowComment;
        }

        public void setAllowComment(String allowComment) {
            this.allowComment = allowComment;
        }

        public SubscribeBean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(SubscribeBean subscribe) {
            this.subscribe = subscribe;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getHasEditor() {
            return hasEditor;
        }

        public void setHasEditor(String hasEditor) {
            this.hasEditor = hasEditor;
        }

        public String getHasVideo() {
            return hasVideo;
        }

        public void setHasVideo(String hasVideo) {
            this.hasVideo = hasVideo;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<RelateDocsBean> getRelateDocs() {
            return relateDocs;
        }

        public void setRelateDocs(List<RelateDocsBean> relateDocs) {
            this.relateDocs = relateDocs;
        }


        public static class SlidesBean implements Serializable {
            private String image;
            private String title;
            private String description;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }


        public static class SubscribeBean {

            private String type;
            private String cateSource;
            private String parentid;
            private String parentname;
            private String cateid;
            private String catename;
            private String logo;
            private String description;
            private String api;
            private int show_link;
            private String share_url;
            private int status;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCateSource() {
                return cateSource;
            }

            public void setCateSource(String cateSource) {
                this.cateSource = cateSource;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getParentname() {
                return parentname;
            }

            public void setParentname(String parentname) {
                this.parentname = parentname;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getApi() {
                return api;
            }

            public void setApi(String api) {
                this.api = api;
            }

            public int getShow_link() {
                return show_link;
            }

            public void setShow_link(int show_link) {
                this.show_link = show_link;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class ImgBean {

            private String url;
            private SizeBean size;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public SizeBean getSize() {
                return size;
            }

            public void setSize(SizeBean size) {
                this.size = size;
            }

            public static class SizeBean {
                private String width;
                private String height;

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }
        }

        public static class RelateDocsBean {


            private String thumbnail;
            private String title;
            private String id;
            private String source;
            private String documentId;
            private String staticId;
            private String createTime;
            private String updateTime;
            private String commentsUrl;
            private String type;
            private LinkBean link;
            private SubscribeBeanX subscribe;
            private String comments;
            private String commentsall;
            private StyleBean style;
            private PhvideoBean phvideo;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getCommentsUrl() {
                return commentsUrl;
            }

            public void setCommentsUrl(String commentsUrl) {
                this.commentsUrl = commentsUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public LinkBean getLink() {
                return link;
            }

            public void setLink(LinkBean link) {
                this.link = link;
            }

            public SubscribeBeanX getSubscribe() {
                return subscribe;
            }

            public void setSubscribe(SubscribeBeanX subscribe) {
                this.subscribe = subscribe;
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

            public StyleBean getStyle() {
                return style;
            }

            public void setStyle(StyleBean style) {
                this.style = style;
            }

            public PhvideoBean getPhvideo() {
                return phvideo;
            }

            public void setPhvideo(PhvideoBean phvideo) {
                this.phvideo = phvideo;
            }


            public static class LinkBean {
                private String type;
                private String url;

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
            }

            public static class SubscribeBeanX {
                private String cateid;
                private String type;
                private String catename;
                private String logo;
                private String description;
                private String cateSource;
                private String api;

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

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCateSource() {
                    return cateSource;
                }

                public void setCateSource(String cateSource) {
                    this.cateSource = cateSource;
                }

                public String getApi() {
                    return api;
                }

                public void setApi(String api) {
                    this.api = api;
                }
            }

            public static class StyleBean {

                private String view;
                private List<String> backreason;

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

            public static class PhvideoBean {

                private String channelName;
                private int length;

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public int getLength() {
                    return length;
                }

                public void setLength(int length) {
                    this.length = length;
                }
            }

        }
    }
}
