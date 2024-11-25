package com.example.ctuintern.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentNewsBinding
import com.example.ctuintern.databinding.FragmentSearchBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ui.news.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : MainFragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var student: Student
    private lateinit var adapter: NewsAdapter
    override fun initView() {
        val user = getCurrentUser()
        if(user != null && user is Student) {
            student = user
            adapter = NewsAdapter(
                addNewsToFavorite = { news -> viewModel.addNewsToFavorites(news.newID.toString(), user.userID) },
                removeNewsFromFavorite = { news -> viewModel.removeNewsFromFavorites(news.newID.toString(), user.userID) },
                showDetail = { news -> showNewsDetail(news) },
                checkFavorite = { news, favoriteView -> viewModel.checkFavorite(news.newID.toString(), user.userID, favoriteView) }
            )
            adapter.setDataset(listOf())
            binding.recycleView.adapter = adapter
            setupSearch()
        }
    }

    private fun setupSearch() {
        // Use a flow to observe search input
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Trigger search when the text changes
                viewModel.onSearchQueryChanged(newText.orEmpty())
                return true
            }
        })
        lifecycleScope.launch {
            viewModel.searchResults.collect { results ->
                adapter.setDataset(results)
            }
        }
    }

    override fun initClick() {
        binding.apply {
            backArrow.setOnClickListener {
                navigateToFragment(root, R.id.action_searchFragment_to_newsFragment)
            }
        }
    }

    override fun showNewsDetail(news: News) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

}