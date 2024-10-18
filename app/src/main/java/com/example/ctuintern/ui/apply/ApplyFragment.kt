package com.example.ctuintern.ui.apply

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentApplyBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyFragment : MainFragment() {
    private var _binding: FragmentApplyBinding? = null
    private val binding get() = _binding!!
    private lateinit var student: Student
    private lateinit var recycleView: RecyclerView
    private val viewModel: ApplyViewModel by viewModels()
    override fun initView() {
        recycleView = binding.recyclerView
        if(getCurrentUser() != null) {
            val user = getCurrentUser()
            if (user is Student) {
                student = user
                binding.profile.text = getCurrentUser()!!.userName
                viewModel.getApplyNews(student.userID) { newsList ->
                    if (newsList.isNullOrEmpty()) {
                        Log.i("NewsFragment", "initView: empty news list")
                    } else {
                        var adapter = AppliedNewsAdapter(
                            showDetail = { appliedNews -> showNewsDetail(appliedNews.news!!) }
                        )
                        adapter.setDataSet(newsList)
                        recycleView.adapter = adapter
                        Log.i("NewsFragment", "initView: ${newsList.size}")
                    }
                }
            }
        }
    }

    override fun initClick() {
        binding.news.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_applyFragment_to_newsFragment)
        }
        binding.videoCall.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_applyFragment_to_interviewFragment)
        }
        binding.intern.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_applyFragment_to_internFragment)
        }
        binding.favorite.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_applyFragment_to_favoriteFragment)
        }
        binding.profile.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_applyFragment_to_profileFragment)
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
        _binding = FragmentApplyBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}