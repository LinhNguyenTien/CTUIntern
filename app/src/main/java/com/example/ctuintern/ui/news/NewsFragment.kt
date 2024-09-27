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
    private lateinit var company: RelativeLayout
    private lateinit var job: RelativeLayout
    private lateinit var location: RelativeLayout
    private lateinit var tech: RelativeLayout
    private lateinit var interview: RelativeLayout
    private lateinit var intern: RelativeLayout
    private lateinit var news: RelativeLayout
    private lateinit var favorite: RelativeLayout
    private lateinit var apply: RelativeLayout
    private lateinit var recycleView: RecyclerView
    private lateinit var student: Student
    private val viewModel: NewsViewModel by viewModels()


    override fun initView() {
        profile = binding.profile
        search = binding.search
        company = binding.company
        job = binding.job
        location = binding.location
        tech = binding.tech
        recycleView = binding.recyclerView
        interview = binding.videoCall
        intern = binding.intern
        news = binding.news
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
                            addNewsToFavorite = { news -> viewModel.addNewsToFavorites(news, user.userID) },
                            removeNewsFromFavorite = { news -> viewModel.removeNewsFromFavorites(news, user.userID) },
                            showDetail = { news -> showNewsDetail(news) }
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

    private fun showNewsDetail(news: News) {
        navigateToFragment(binding.root, NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(news))
    }

    override fun initClick() {
        profile.setOnClickListener {
            // navigate to profile
        }
        search.setOnClickListener {
            // navigate to search
        }
        company.setOnClickListener {

        }
        job.setOnClickListener {

        }
        location.setOnClickListener {

        }
        tech.setOnClickListener {

        }
        interview.setOnClickListener {

        }
        intern.setOnClickListener {

        }
        news.setOnClickListener {

        }
        favorite.setOnClickListener {

        }
        apply.setOnClickListener {

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