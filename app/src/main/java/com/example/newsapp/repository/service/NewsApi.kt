package com.example.newsapp.repository.service

import android.provider.SyncStateContract
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBrakingNews(
        @Query("country") country : String = "in",  // you can add your country like us,canada ,england etc
        @Query("page") pageNumber : Int ,
        @Query ("apiKey") apiKey : String = Constants.API_KEY
    ) : Response<NewsResponse>

    @GET ("v2/everything")
    suspend fun getSearchingNews (
        @Query("country") country: String = "in" ,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>


}