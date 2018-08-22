package com.nytimes.articles.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.articles.R;
import com.nytimes.articles.common.Constants;

import com.nytimes.articles.databinding.FragmentArticleDetailsBinding;
import com.nytimes.articles.utils.FragmentUtils;
import com.nytimes.articles.view.base.BaseFragment;
import com.nytimes.articles.viewmodel.ArticleDetailsViewModel;
import com.nytimes.yellowdependency.YellowFragment;

/**
 * The article list fragment which is responsible for showing the article details
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public class ArticleDetailFragment extends BaseFragment<ArticleDetailsViewModel, FragmentArticleDetailsBinding> {
    @Override
    protected Class<ArticleDetailsViewModel> getViewModel() {
        return ArticleDetailsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_article_details;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if(null != args) {
            viewModel.setUrl(args.getString(Constants.BUNDLE_KEY_ARTICLE_URL));
            viewModel.loadArticleDetails();
        }
        viewModel.getArticleEntityMutableLiveData().observe(this, articleEntity -> {
            if(null != articleEntity && null != args) {
                dataBinding.textTitle.setText(articleEntity.getTitle());
                dataBinding.textContent.setText(articleEntity.getContent());
                dataBinding.textPublishedDate.setText(args.getString(Constants.BUNDLE_KEY_ARTICLE_PUBLISHED_DATE));
                dataBinding.loadingProgress.setVisibility(View.GONE);
            }
        });

        viewModel.getErrorMessageRecieved().observe(this, message ->{
            dataBinding.loadingProgress.setVisibility(View.GONE);
            dataBinding.textContent.setText(getActivity().getString(R.string.networkError));
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(item.getItemId() == R.id.navigate_button){
            YellowFragment yellowFragment = new YellowFragment();
            FragmentUtils.replaceFragment((AppCompatActivity) getActivity(), yellowFragment, R.id.fragContainer, true, FragmentUtils.TRANSITION_POP);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
