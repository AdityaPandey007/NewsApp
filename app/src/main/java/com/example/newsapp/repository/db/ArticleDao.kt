package com.example.newsapp.repository.db

import androidx.appcompat.widget.`AppCompatRadioButton$InspectionCompanion`
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.Article

interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article :Article) : Long

    @Query(" SELECT * FROM articles")
    fun  getArticle() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}