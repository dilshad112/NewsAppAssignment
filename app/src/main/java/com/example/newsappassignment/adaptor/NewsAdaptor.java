package com.example.newsappassignment.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsappassignment.BR;
import com.example.newsappassignment.R;
import com.example.newsappassignment.databinding.CardRowBinding;
import com.example.newsappassignment.model.Newsdata;

import java.util.List;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.ViewHolder> {


    // news list to hold all list articles
    private List<Newsdata> newsdataList;

    //constructor with one list parameter
    public NewsAdaptor(List<Newsdata> list){
        newsdataList=list;
    }
    //this method clear current and update adaptor with new list
    @SuppressLint("NotifyDataSetChanged")
    public void setNewData(List<Newsdata> list){
        newsdataList.clear();
        newsdataList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.card_row, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Newsdata dataModel = newsdataList.get(position);

        holder.bind(dataModel);

    }

    @Override
    public int getItemCount() {
        return newsdataList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardRowBinding cardRowBinding;
        public ViewHolder(CardRowBinding binding) {
            super(binding.getRoot());
            cardRowBinding=binding;
        }
        public void bind(Object obj) {
            cardRowBinding.setVariable(BR.model, obj);
            cardRowBinding.executePendingBindings();
        }
    }
}
