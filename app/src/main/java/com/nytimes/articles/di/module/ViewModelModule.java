package com.nytimes.articles.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.nytimes.articles.viewmodel.ArticleDetailsViewModel;
import com.nytimes.articles.viewmodel.ArticleListViewModel;
import com.nytimes.articles.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Alloes us to inject dependencies via constructor injection
 * <p>
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsArticleListViewModel(ArticleListViewModel articleListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailsViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsArticleDetailsiewModel(ArticleDetailsViewModel articleDetailsViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
