package com.example.ctuintern.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentReviewDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDetailFragment : MainFragment() {
    private var _binding: FragmentReviewDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ReviewDetailFragmentArgs by navArgs()
    private val review = args.review
    override fun initView() {
        binding.apply {
            companyName.text = "Đơn vị: " + review.employer.userName
            companyPhone.text = "Số điện thoại: " + review.employer.phone
            size.text = "Qui mô: " + review.employer.size + "Nhân viên"
            when(review.options) {
                1 -> check1.isChecked = true
                2 -> check2.isChecked = true
                3 -> check3.isChecked = true
                4 -> check4.isChecked = true
                else -> check5.isChecked = true
            }
            otherReview.text = review.content
        }
    }

    override fun initClick() {
        binding.backArrow.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
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
        _binding = FragmentReviewDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

}