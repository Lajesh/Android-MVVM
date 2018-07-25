package com.nytimes.articles.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.nytimes.articles.data.local.dao.ArticleDao;
import com.nytimes.articles.data.local.entity.ArticleEntity;

/**
 * File Description
 * <p>
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
@Database(entities = {ArticleEntity.class}, version = 2)
public abstract class ArticleDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}