package com.nytimes.articles.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.nytimes.articles.data.local.entity.ArticleEntity;
import com.nytimes.articles.databinding.ItemArticleListBinding;
import com.nytimes.articles.view.base.BaseAdapter;
import com.nytimes.articles.view.callbacks.ArticleListCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Article list recyclerview adapter
 * Author: Lajesh D
 * Email: lajeshds2007@gmail.com
 * Created: 7/24/2018
 * Modified: 7/24/2018
 */
public class ArticleListAdapter extends  BaseAdapter<ArticleListAdapter.ArticleViewHolder, ArticleEntity>
implements Filterable{

    private List<ArticleEntity> articleEntities;

    private List<ArticleEntity> articleEntitiesFiltered;

    private final ArticleListCallback articleListCallback;

    public ArticleListAdapter(@NonNull ArticleListCallback articleListCallback) {
        articleEntities = new ArrayList<>();
        articleEntitiesFiltered = new ArrayList<>();
        this.articleListCallback = articleListCallback;
    }

    @Override
    public void setData(List<ArticleEntity> entities) {
        this.articleEntities = entities;
        this.articleEntitiesFiltered = entities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return ArticleViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, articleListCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder viewHolder, int i) {
        viewHolder.onBind(articleEntitiesFiltered.get(i));
    }

    @Override
    public int getItemCount() {
        return articleEntitiesFiltered.size();
    }

    @Override
    public Filter getFilter() {
         return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleEntitiesFiltered = articleEntities;
                } else {
                    List<ArticleEntity> filteredList = new ArrayList<>();
                    for (ArticleEntity row : articleEntities) {

                        // name match condition. this might differ depending on your requirement
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthors().toLowerCase().contains(charString.toLowerCase())
                                || row.getPublishedDate().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    articleEntitiesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleEntitiesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleEntitiesFiltered = (ArrayList<ArticleEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        private static ArticleViewHolder create(LayoutInflater inflater, ViewGroup parent, ArticleListCallback callback) {
            ItemArticleListBinding itemMovieListBinding = ItemArticleListBinding.inflate(inflater, parent, false);
            return new ArticleViewHolder(itemMovieListBinding, callback);
        }

        final ItemArticleListBinding binding;

        private ArticleViewHolder(ItemArticleListBinding binding, ArticleListCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    callback.onArticleClicked(binding.getArticle()));
        }

        private void onBind(ArticleEntity articleEntity) {
            binding.setArticle(articleEntity);
            binding.executePendingBindings();
        }
    }
}
