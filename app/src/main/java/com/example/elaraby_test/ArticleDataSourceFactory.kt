package com.younis.newapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.elaraby_test.Article

class ArticleDataSourceFactory : androidx.paging.DataSource.Factory<Int, Article>() {
    val articlesLiveDataSource = MutableLiveData<ArticleDataSource>()
    lateinit var listId: String

    override fun create(): androidx.paging.DataSource<Int, Article> {
        val articlesDataSource = ArticleDataSource()
        articlesLiveDataSource.postValue(articlesDataSource)

        return articlesDataSource
    }

}