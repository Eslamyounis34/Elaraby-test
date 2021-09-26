package com.younis.newapp.repositories

import android.app.Application
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedListAdapter
import com.example.elaraby_test.Article
import com.example.elaraby_test.RetroInstance
import com.younis.newapp.model.Constants
import com.younis.newapp.model.Constants.Companion.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleDataSource() : PageKeyedDataSource<Int, Article>() {

    lateinit var application: Application
    var disposable: Disposable? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        disposable = RetroInstance.api.getBreakingNewsByPage("us", "business", API_KEY, FIRST_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val responseItems = it.articles
                responseItems.let {
                    callback.onResult(responseItems, null, FIRST_PAGE + 1)
                }
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        disposable = RetroInstance.api.getBreakingNewsByPage("us", "business", API_KEY, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val responseItems = it.articles
                val key = if (params.key > 1) params.key - 1 else 0
                responseItems.let {
                    callback.onResult(responseItems, key)
                }
            }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        disposable = RetroInstance.api.getBreakingNewsByPage("us", "business", API_KEY, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val responseItems = it.articles
                val key = params.key + 1
                responseItems.let {
                    callback.onResult(responseItems, key)
                }
            }
    }

    companion object {
        const val PAGE_SIZE = 10
        const val FIRST_PAGE = 1

    }
}