package com.example.ctuintern.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.data.model.User
import com.example.ctuintern.databinding.FragmentFavoriteBinding
import com.example.ctuintern.databinding.FragmentNewsBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : MainFragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycleView: RecyclerView
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var student: Student
    override fun initView() {
        recycleView = binding.recyclerView
        if(getCurrentUser() != null) {
            val user = getCurrentUser()
            if (user is Student) {
                student = user
                binding.profile.text = getCurrentUser()!!.userName
                viewModel.getFavoriteNews(student.userID) { newsList ->
                    if (newsList.isNullOrEmpty()) {
                        Log.i("NewsFragment", "initView: empty news list")
                    } else {
                        var adapter = NewsAdapter(
                            addNewsToFavorite = { },
                            removeNewsFromFavorite = { news ->
                                viewModel.removeNewsFromFavorites(
                                    news,
                                    getCurrentUser()!!.userID
                                )
                            },
                            showDetail = { news -> showNewsDetail(news) }
                        )
                        adapter.setDataset(newsList)
                        recycleView.adapter = adapter
                        Log.i("NewsFragment", "initView: ${newsList.size}")
                    }
                }
            }
        }
    }

    override fun initClick() {
        binding.news.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_favoriteFragment_to_newsFragment)
        }
        binding.videoCall.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_favoriteFragment_to_interviewFragment)
        }
        binding.intern.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_favoriteFragment_to_internFragment)
        }
        binding.apply.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_favoriteFragment_to_applyFragment)
        }
        binding.profile.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_favoriteFragment_to_profileFragment)
        }
    }

    override fun showNewsDetail(news: News) {
        navigateToFragment(binding.root, FavoriteFragmentDirections.actionFavoriteFragmentToNewsDetailFragment(news))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}