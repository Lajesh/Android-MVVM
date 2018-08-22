package com.nytimes.yellowdependency;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * File Description
 * Author: Lajesh
 * Company: Farabi Technologies
 * Email: Lajesh@farabi.ae
 * Created: 8/22/18
 * Modified: 8/22/18
 */
public class YellowFragment extends Fragment {

    private YellowViewModel yellowViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_yellow, container, false);
        return layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yellowViewModel = ViewModelProviders.of(this).get(YellowViewModel.class);
    }
}
