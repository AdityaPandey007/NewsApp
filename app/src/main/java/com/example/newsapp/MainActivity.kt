package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.repository.db.ArticleDatabase
import com.example.newsapp.repository.service.NewsRepository
import com.example.newsapp.viewModel.NewsViewModel
import com.example.newsapp.viewModel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this) as ArticleDatabase)
        val viewModelProvider = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this , viewModelProvider).get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsFragment.findNavController())
    }
}