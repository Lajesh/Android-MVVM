package com.nytimes.articles.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.nytimes.articles.R;
import com.nytimes.articles.databinding.ActivityMainBinding;
import com.nytimes.articles.managers.LocaleManager;
import com.nytimes.articles.utils.FragmentUtils;
import com.nytimes.articles.utils.SharedPrefUtil;
import com.nytimes.articles.view.base.BaseActivity;
import com.nytimes.articles.view.fragment.ArticleListFragment;

import static com.nytimes.articles.common.Constants.LANGUAGE_ARABIC;
import static com.nytimes.articles.common.Constants.LANGUAGE_ENGLISH;
import static com.nytimes.articles.common.Constants.LANGUAGE_KEY;
import static com.nytimes.articles.utils.FragmentUtils.TRANSITION_NONE;

public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        FragmentUtils.replaceFragment(this, ArticleListFragment.newInstance(), R.id.fragContainer, false, TRANSITION_NONE);

        dataBinding.languageSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1)
                    setNewLocale(LANGUAGE_ARABIC, false);
                else
                    setNewLocale(LANGUAGE_ENGLISH, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Nothing goes here
            }
        });
        if(null != getSupportActionBar())
            getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private void setLanguage(){
        String lang = SharedPrefUtil.getStringPreference(this, LANGUAGE_KEY,LANGUAGE_ENGLISH );
        if(lang.equalsIgnoreCase(LANGUAGE_ENGLISH))
            dataBinding.languageSelectionSpinner.setSelection(0, false);
        else
            dataBinding.languageSelectionSpinner.setSelection(1, false);

    }
}
