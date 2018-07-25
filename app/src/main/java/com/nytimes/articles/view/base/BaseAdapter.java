package com.nytimes.articles.view.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Generic Base adapter for recycler views
 * <p>
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T>{

    public abstract void setData(List<D> data);
}