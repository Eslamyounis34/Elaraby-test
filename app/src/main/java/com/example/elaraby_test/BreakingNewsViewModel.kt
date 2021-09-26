package com.example.elaraby_test

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.younis.newapp.repositories.ArticleDataSource
import com.younis.newapp.repositories.ArticleDataSourceFactory

class BreakingNewsViewModel(application: Application):AndroidViewModel(application) {
    val articlePagedList: LiveData<PagedList<Article>>
    private val liveDataSource: LiveData<ArticleDataSource>
    private val itemDataSourceFactory = ArticleDataSourceFactory()
    var   checkExist=false

    init {
        liveDataSource = itemDataSourceFactory.articlesLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ArticleDataSource.PAGE_SIZE)
            .build()
        articlePagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
        Log.e("CheckData", articlePagedList.toString())
    }
}