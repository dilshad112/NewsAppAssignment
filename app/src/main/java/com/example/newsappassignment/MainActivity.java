package com.example.newsappassignment;

import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;


import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsappassignment.adaptor.NewsAdaptor;
import com.example.newsappassignment.databinding.ActivityMainBinding;
import com.example.newsappassignment.model.Newsdata;
import com.example.newsappassignment.viewModel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ActivityMainBinding binding;
    private List<Newsdata> mainList =new ArrayList<>();
    private NewsAdaptor adapter;
    private NewsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        setSupportActionBar(binding.toolbar);

        initViews();



         viewModel=new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getNewsList().observe(this, new Observer<List<Newsdata>>() {
            @Override
            public void onChanged(List<Newsdata> newsdata) {
                if(adapter==null) {
                    binding.progressBar.setVisibility(View.GONE);
                    adapter = new NewsAdaptor( newsdata);
                    mRecyclerView.setAdapter(adapter);
                    mainList.addAll(newsdata);
                }else {
                    adapter.setNewData(newsdata);
                }

            }
        });
    }

    private void initViews(){
        mRecyclerView =binding.cardRecyclerView;
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.performSearch(mainList,newText);

                return true;
            }
        });
    }
}
