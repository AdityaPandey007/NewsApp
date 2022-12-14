package com.example.newsapp.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.repository.service.RetroFitClient
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class ArticleDataSource(val scope : CoroutineScope) : PageKeyedDataSource<Int , Article>(){

    // For breaking news
    val breakingNews : MutableLiveData<MutableList<Article>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse : NewsResponse? = null

    // for searching news

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse : NewsResponse? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>) {

        scope.launch {
            try{
                val response = RetroFitClient.api.getBrakingNews("in",1,Constants.API_KEY)
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }

            }catch (exception : Exception){
                Log.e("DataSource ::" , exception.message.toString())
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

        scope.launch {
            try {
                val response = RetroFitClient.api.getBrakingNews(
                    "in",
                    params.requestedLoadSize,
                    Constants.API_KEY
                )
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it,  params.key + 1)
                        }
                    }
                }

            } catch (exception: Exception) {
                Log.e("DataSource ::", exception.message.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        TODO("Not yet implemented")
    }
}