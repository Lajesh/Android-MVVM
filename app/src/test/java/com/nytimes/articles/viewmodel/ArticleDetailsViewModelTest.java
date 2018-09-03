package com.nytimes.articles.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.nytimes.articles.data.local.entity.ArticleEntity;
import com.nytimes.articles.data.remote.repository.ArticleRepository;
import com.nytimes.articles.utils.SingleLiveEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.notNullValue;
import android.arch.lifecycle.MutableLiveData;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for ArticleDetailViewModel
 * <p>
 * Author: Lajesh D
 * Email: lajesh@farabi.ae
 * Created: 9/3/2018
 * Modified: 9/3/2018
 */
@RunWith(JUnit4.class)
public class ArticleDetailsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    ArticleDetailsViewModel viewModel;

    ArticleRepository repo;

    @Before
    public void setup(){
        repo = mock(ArticleRepository.class);
        viewModel = new ArticleDetailsViewModel(repo);
    }

    @Test
    public void checkNull(){
        assertThat(repo, notNullValue());
        assertThat(viewModel, notNullValue());
    }

    @Test
    public void checkArticleEntityLiveData(){
        MutableLiveData<ArticleEntity> articleEntityMutableLiveData = new MutableLiveData<>();
        viewModel.setArticleEntityMutableLiveData(articleEntityMutableLiveData);
        assertThat(viewModel.getArticleEntityMutableLiveData(), notNullValue());
    }

    @Test
    public void checkUrl(){
        viewModel.setUrl("testUrl");
        assertThat(viewModel.getUrl(), is("testUrl"));
    }

    @Test
    public void checkErrorMessage(){
        viewModel.setErrorMessageRecieved(new SingleLiveEvent<>());
        assertThat(viewModel.getErrorMessageRecieved(), notNullValue());
    }

    @Test
    public void checkArticleDetailsResponse(){
        viewModel = new ArticleDetailsViewModel(null);
        viewModel.loadArticleDetails();
        viewModel = new ArticleDetailsViewModel(repo);
        viewModel.loadArticleDetails();
    }

}
