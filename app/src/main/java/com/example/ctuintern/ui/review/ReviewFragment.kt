package com.example.ctuintern.ui.review
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.databinding.FragmentProfileBinding
import com.example.ctuintern.databinding.FragmentReviewBinding
import com.example.ctuintern.ui.classManagement.ClassAdapter
import com.example.ctuintern.ui.classManagement.ClassManagementFragmentDirections
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ReviewFragment : MainFragment() {
    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var teacher: Teacher
    private val viewModel: ReviewViewModel by viewModels()
    override fun initView() {
        val user = getCurrentUser()
        if(user != null && user is Teacher) {
            teacher = user
            binding.profile.text = teacher.userName
            viewModel.initView(teacher.userID)
            viewModel.reviews.observe(viewLifecycleOwner, Observer { reviews ->
                val reviewAdapter = ReviewAdapter() { review ->
                    navigateToFragment(binding.root, ReviewFragmentDirections.actionReviewFragmentToReviewDetailFragment(review))
                }
                reviewAdapter.setDataset(reviews)
                binding.recyclerView.adapter = reviewAdapter
            })
        }
    }
    override fun initClick() {
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
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}