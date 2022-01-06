package com.example.newsappassignment.viewModel;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsappassignment.model.Newsdata;
import com.example.newsappassignment.model.Newsdata2;
import com.example.newsappassignment.utils.NewsApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    // use this format to convert time format
    public static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private MutableLiveData<List<Newsdata>> newsList;

    //we will call this method to get the data
    public LiveData<List<Newsdata>> getNewsList() {
        //if the list is null
        if (newsList == null) {
            newsList = new MutableLiveData<List<Newsdata>>();
            //we will load it asynchronously from server in this method
            loadNews();
        }

        //finally we will return the list
        return newsList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<Newsdata2> call = newsApi.getNews();


        call.enqueue(new Callback<Newsdata2>() {
            @Override
            public void onResponse(Call<Newsdata2> call, Response<Newsdata2> response) {

                //finally we are setting the list to our MutableLiveData
                List<Newsdata> list=changeTime(response.body().articles);
                newsList.setValue(list);
                Log.i("","");
            }

            @Override
            public void onFailure(Call<Newsdata2> call, Throwable t) {
                Log.i("","");
            }
        });
    }

    // this method change time format
    private List<Newsdata> changeTime(List<Newsdata> list){
        for (int i = 0; i < list.size(); i++) {
            Newsdata newsdata=list.get(i);
            newsdata.setPublishedAt(formateTime(newsdata.getPublishedAt()));
            list.set(i,newsdata);
        }
        return list;
    }

    private String formateTime(String dt){
        String dateStr = "2016-01-24T16:00:00.000Z";
        Date date = null;
        try {
            date = inputFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String niceDateStr = (String) DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
        return niceDateStr;
    }
    // search query string in list
    public void performSearch(List<Newsdata> list,String query){
        List<Newsdata> l=filter(list,query);
        newsList.postValue(l);
    }

    // filter news list and return a new list
    private  List<Newsdata> filter(List<Newsdata> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Newsdata> filteredModelList = new ArrayList<>();
        for (Newsdata model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery) ) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
