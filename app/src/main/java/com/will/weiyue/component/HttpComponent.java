package com.will.weiyue.component;

import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.jandan.JdDetailFragment;
import com.will.weiyue.ui.news.ArticleReadActivity;
import com.will.weiyue.ui.news.DetailFragment;
import com.will.weiyue.ui.news.ImageBrowseActivity;
import com.will.weiyue.ui.news.NewsFragment;
import com.will.weiyue.ui.video.VideoFragment;

import dagger.Component;

/**
 * Created by android on 2018/1/16.
 */

@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(DetailFragment detailFragment);

    void inject(NewsFragment newsFragment);

    void inject(ArticleReadActivity articleReadActivity);

    void inject(ImageBrowseActivity imageBrowseActivity);

    void inject(VideoFragment videoFragment);

    void inject(com.will.weiyue.ui.video.DetailFragment detailFragment);

    void inject(JdDetailFragment jdDetailFragment);
}
