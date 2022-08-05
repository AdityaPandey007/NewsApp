package com.example.newsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.adapters.SavedNewsAdapter
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.shareNews
import com.example.newsapp.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlin.random.Random

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: SavedNewsAdapter
    val TAG ="SavedNewsFragment"

    private fun setUpRecyclerView(){

        newsAdapter = SavedNewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article" , it)
            }
            findNavController().navigate(R.id.action_SavedNewsFragment_to_articleFragment , bundle)
        }


        newsAdapter.onShareClickListener {
            shareNews(context , it)
        }

        // swipe to delete

        val onItemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                Snackbar.make(requireView(), "Deleted Successfully", Snackbar.LENGTH_LONG).apply {

                    setAction("Undo"){
                        viewModel.insertArticle((article))
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(onItemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
        setViewModelObserver()
    }

    private fun setViewModelObserver() {

        viewModel = (activity as MainActivity).viewModel

        viewModel.getSavedArticle().observe(viewLifecycleOwner , Observer {
            Log.i(TAG , ""+it.size)
            newsAdapter.differ.submitList(it)
            rvSavedNews.visibility = View.VISIBLE
            shimmerFrameLayout2.stopShimmerAnimation()
            shimmerFrameLayout2.visibility = View.GONE
        })
    }
}