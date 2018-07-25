package com.nytimes.articles.di.builder;

import com.nytimes.articles.view.fragment.ArticleDetailFragment;
import com.nytimes.articles.view.fragment.ArticleListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This builder provides android injector service to fragments
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
@Module
public abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract ArticleListFragment contributeArticleListFragment();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract ArticleDetailFragment contributeArticleDetailFragment();

}
