package com.nytimes.articles.viewmodel;

import com.nytimes.articles.data.remote.repository.ArticleRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.notNullValue;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.MatcherAssert.assertThat;



/**
 * Unit test for ArticleListViewModel
 * <p>
 * Author: Lajesh D
 * Email: lajesh@farabi.ae
 * Created: 9/2/2018
 * Modified: 9/2/2018
 */
@RunWith(JUnit4.class)
public class ArticleListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    ArticleListViewModel viewModel;

    ArticleRepository repo;

    @Before
    public void setup(){
        repo = mock(ArticleRepository.class);
        viewModel = new ArticleListViewModel(repo);
    }

    @Test
    public void checkNull(){
        assertThat(viewModel, notNullValue());
        assertThat(repo, notNullValue());
    }

    @Test
    public void checkResponse(){
        repo.loadPopularArticles(7);
        assertThat(viewModel.getPopularArticles(), nullValue());
    }
}
