package com.example.news;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.api.ApiManager;
import com.example.news.model.ArticlesItem;
import com.example.news.model.NewsResponse;
import com.example.news.model.SourcesItem;
import com.example.news.model.SourcesResponse;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {

    protected TabLayout tablayout;
    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home_page);
        initView();
        initRecyclerView();
        getNewsSoursces();
    }
    NewsAdaptar adaptar;
    RecyclerView.LayoutManager layoutManager;
    private void initRecyclerView() {
        adaptar=new NewsAdaptar(null);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptar);
    }

    private void getNewsSoursces() {
        ApiManager.getApis()
                .getNewsSources(constants.Api_Key)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                        if ("ok".equals(response.body().getStatus())){
                            List<SourcesItem> sources =
                                    response.body()
                                    .getSources();
                            initTabLatout(sources);
                        }else {
                            Toast.makeText(HomePage.this, response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        Toast.makeText(HomePage.this, "please try again later"+
                                t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTabLatout(List<SourcesItem> sources) {
        for (SourcesItem source : sources){
            TabLayout.Tab tab=tablayout.newTab();
            tab.setText(source.getName());
            tab.setTag(source);
            tablayout.addTab(tab);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                String sourceId=sourcesItem.getId();
                getNewsBySourscesId(sourceId);
            }
        });
        tablayout.getTabAt(0).select();
    }

    private void getNewsBySourscesId(String sourceId) {
        ApiManager.getApis()
                .getNewsBySourcesId(constants.Api_Key, sourceId)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        if ("ok".equals(response.body().getStatus())){
                            List<ArticlesItem> newslist =
                                    response.body()
                                            .getArticles();
                            adaptar.changeData(newslist);

                        }else {
                            Toast.makeText(HomePage.this, response.body().massage, Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        Toast.makeText(HomePage.this, "please try again later"+
                                t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }
}
