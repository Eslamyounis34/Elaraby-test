package com.example.elaraby_test

import com.younis.newapp.model.Constants
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface NewsApi {
    @GET("/v2/top-headlines")
    fun getBreakingNews(
        @Query("country") country: String ,
        @Query("category") category: String ,
        @Query("apiKey") apiKey: String
    ): Observable<NewsResponse>

    @GET("/v2/top-headlines")
    fun getBreakingNewsByPage(
        @Query("country") country: String ,
        @Query("category") category: String ,
        @Query("apiKey") apiKey: String,
        @Query("page")page:Int
    ): Observable<NewsResponse>

    @GET("/v2/everything")
    fun searchNews(
        @Query("apiKey")apiKey: String,
        @Query("q")q:String,
        @Query("sortBy")sortBy:String

    ):Observable<NewsResponse>

    @GET("/v2/everything")
    fun searchNewsByPage(
        @Query("apiKey")apiKey: String,
        @Query("q")q:String,
        @Query("sortBy")sortBy:String,
        @Query("page")page:Int

    ):Single<NewsResponse>

    @Headers(

            "Authorization: key="+Constants.SERVER_KEY ,
            "Content-Type: application/json"
        )
    @POST("fcm/send")
    fun sendCustomNotification(
     @Body notificationContent: NotificationContent
    ):Single<NotificationResponse>
}