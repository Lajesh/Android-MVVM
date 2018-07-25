package com.nytimes.articles.view.callbacks;

import com.nytimes.articles.data.local.entity.ArticleEntity;

public interface ResponseListener {

    void onSuccess(ArticleEntity data);
    void onFailure(String message);
}
