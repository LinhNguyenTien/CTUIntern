package com.example.ctuintern.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctuintern.databinding.FragmentNewsDetailBinding
import com.example.ctuintern.ui.main.MainFragment

class NewsDetailFragment : MainFragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!
    override fun initView() {

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
        // Inflate the layout for this fragment
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

}