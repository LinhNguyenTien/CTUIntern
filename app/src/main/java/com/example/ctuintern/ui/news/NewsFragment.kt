package com.example.ctuintern.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentNewsBinding
import com.example.ctuintern.databinding.RegisterFailDialogBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : MainFragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var profile: TextView
    private lateinit var search: SearchView
    private lateinit var interview: RelativeLayout
    private lateinit var intern: RelativeLayout
    private lateinit var favorite: RelativeLayout
    private lateinit var apply: RelativeLayout
    private lateinit var recycleView: RecyclerView
    private lateinit var student: Student
    private val viewModel: NewsViewModel by viewModels()


    override fun initView() {
        profile = binding.profile
        search = binding.search
        recycleView = binding.recyclerView
        interview = binding.videoCall
        intern = binding.intern
        favorite = binding.favorite
        apply = binding.apply
        if(getCurrentUser() != null) {
            val user = getCurrentUser()
            if(user is Student) {
                student = user
                profile.text = user.userName

                viewModel.getNews { newsList ->
                    if(newsList.isNullOrEmpty()) {
                        Log.i("NewsFragment", "initView: empty news list")
                    }
                    else {
                        var adapter = NewsAdapter(
                            addNewsToFavorite = { news -> viewModel.addNewsToFavorites(news.newID.toString(), user.userID) },
                            removeNewsFromFavorite = { news -> viewModel.removeNewsFromFavorites(news.newID.toString(), user.userID) },
                            showDetail = { news -> showNewsDetail(news) },
                            checkFavorite = { news, favoriteView -> viewModel.checkFavorite(student.userID, news.newID.toString(), favoriteView) }
                        )
                        adapter.setDataset(newsList)
                        recycleView.adapter = adapter
                        Log.i("NewsFragment", "initView: ${newsList.size}")
                    }
                }
            }
            else {
                Log.i("NewsFragment", "initView: user is not student")
            }
        }
        else {
            Log.i("NewsFragment", "initView: user is null")
        }
    }

    override fun showNewsDetail(news: News) {
        navigateToFragment(binding.root, NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(news))
    }

    override fun initClick() {
        profile.setOnClickListener {
            // navigate to profile
            navigateToFragment(binding.root, R.id.action_newsFragment_to_profileFragment)
        }
        search.setOnClickListener {
            // navigate to search
            navigateToFragment(binding.root, R.id.action_newsFragment_to_searchFragment)
        }
        interview.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_newsFragment_to_interviewFragment)
        }
        intern.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_newsFragment_to_internFragment)
        }
        favorite.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_newsFragment_to_favoriteFragment)
        }
        apply.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_newsFragment_to_applyFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }


}