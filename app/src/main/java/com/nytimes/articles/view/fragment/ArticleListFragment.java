package com.nytimes.articles.view.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.articles.R;
import com.nytimes.articles.common.Constants;
import com.nytimes.articles.data.local.entity.ArticleEntity;
import com.nytimes.articles.data.remote.Status;
import com.nytimes.articles.databinding.FragmentListArticlesBinding;
import com.nytimes.articles.utils.FragmentUtils;
import com.nytimes.articles.view.adapter.ArticleListAdapter;
import com.nytimes.articles.view.base.BaseFragment;
import com.nytimes.articles.view.callbacks.ArticleListCallback;
import com.nytimes.articles.viewmodel.ArticleListViewModel;


/**
 * The article list fragment which will list the popular articles
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public class ArticleListFragment extends BaseFragment<ArticleListViewModel, FragmentListArticlesBinding> implements ArticleListCallback{

    public static ArticleListFragment newInstance() {
        Bundle args = new Bundle();
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Class<ArticleListViewModel> getViewModel() {
        return ArticleListViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_list_articles;
    }

    @Override
    public void onArticleClicked(ArticleEntity articleEntity) {
        if(null != getActivity()){
            Bundle args = new Bundle();
            args.putString(Constants.BUNDLE_KEY_ARTICLE_URL, articleEntity.getUrl());
            args.putString(Constants.BUNDLE_KEY_ARTICLE_PUBLISHED_DATE, articleEntity.getPublishedDate());
            ArticleDetailFragment detailFragment = new ArticleDetailFragment();
            detailFragment.setArguments(args);
            FragmentUtils.replaceFragment((AppCompatActivity) getActivity(), detailFragment, R.id.fragContainer, true, FragmentUtils.TRANSITION_SLIDE_LEFT_RIGHT);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.recyclerView.setAdapter(new ArticleListAdapter(this));
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        viewModel.getPopularArticles()
                .observe(this, listResource -> {
                    if(null != listResource && (listResource.status == Status.ERROR || listResource.status == Status.SUCCESS)){
                        dataBinding.loginProgress.setVisibility(View.GONE);
                    }
                    dataBinding.setResource(listResource);

                    // If the cached data is already showing then no need to show the error
                    if(null != dataBinding.recyclerView.getAdapter() && dataBinding.recyclerView.getAdapter().getItemCount() > 0){
                        dataBinding.errorText.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(null == getActivity())
            return;

        SearchView searchView;
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if(null != dataBinding.recyclerView.getAdapter())
                    ((ArticleListAdapter)dataBinding.recyclerView.getAdapter()).getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if(null != dataBinding.recyclerView.getAdapter())
                    ((ArticleListAdapter)dataBinding.recyclerView.getAdapter()).getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
