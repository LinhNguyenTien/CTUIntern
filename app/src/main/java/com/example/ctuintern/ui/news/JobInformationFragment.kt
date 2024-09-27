package com.example.ctuintern.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentJobInformationBinding
import com.example.ctuintern.ui.main.MainFragment

class JobInformationFragment(val news: News) : MainFragment() {
    private var _binding: FragmentJobInformationBinding? = null
    private val binding get() = _binding!!
    override fun initView() {
        binding.job.text = news.title
        binding.salary.text = news.salary.toString() + " VNĐ"
        binding.postDay.text = news.postDay
        binding.location.text = news.location
        binding.quantity.text = news.quantity.toString() + " Người"
        binding.expiredDay.text = news.expireDay
        binding.description.text = news.content
    }

    override fun initClick() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobInformationBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }
}