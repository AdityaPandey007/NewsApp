package com.example.newsapp.repository.service

import com.example.newsapp.model.Article
import com.example.newsapp.repository.db.ArticleDatabase

class NewsRepository( val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode :String , pageNumber: Int) =
        RetroFitClient.api.getBrakingNews(countryCode,pageNumber)

    suspend fun getSearchNews(q :String , pageNumber: Int) =
        RetroFitClient.api.getBrakingNews(q,pageNumber)

    suspend fun upsert(article : Article) = db.getArticleDao().insert(article)
    suspend fun delete(article: Article) = db.getArticleDao().insert(article)

    fun getAllArticle() = db.getArticleDao().getArticle()
}