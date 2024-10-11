package com.example.ctuintern.ui.interview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.R
import com.example.ctuintern.data.adapter.NewsAdapter
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentFavoriteBinding
import com.example.ctuintern.databinding.FragmentInterviewBinding
import com.example.ctuintern.ui.apply.AppliedNewsAdapter
import com.example.ctuintern.ui.favorite.FavoriteFragmentDirections
import com.example.ctuintern.ui.favorite.FavoriteViewModel
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.filterList

@AndroidEntryPoint
class InterviewFragment : MainFragment() {
    private var _binding: FragmentInterviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InterviewViewModel by viewModels()
    private lateinit var student: Student

    override fun initView() {
        if(getCurrentUser() != null) {
            val user = getCurrentUser()
            if (user is Student) {
                student = user
                binding.profile.text = getCurrentUser()!!.userName
                viewModel.getApplyNews(student.userID) { newsList ->
                    val adapter = AppliedNewsAdapter { news ->
                        navigateToFragment(binding.root, InterviewFragmentDirections.actionInterviewFragmentToInterviewDetailFragment(news))
                    }
                    adapter.setDataSet(newsList.filter { it.room != null })
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun initClick() {
        binding.profile.setOnClickListener {
            navigateToFragment(binding.root, R.id.action_interviewFragment_to_profileFragment)
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
        _binding = FragmentInterviewBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}