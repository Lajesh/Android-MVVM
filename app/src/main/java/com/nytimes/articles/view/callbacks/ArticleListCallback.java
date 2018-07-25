package com.nytimes.articles.view.callbacks;

import com.nytimes.articles.data.local.entity.ArticleEntity;

/**
 * File Description
 * <p>
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public interface ArticleListCallback {
    void onArticleClicked(ArticleEntity articleEntity);
}

