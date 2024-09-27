package com.example.ctuintern.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentNewsBinding
import com.example.ctuintern.databinding.FragmentProfileBinding
import com.example.ctuintern.ui.main.MainFragment

class ProfileFragment : MainFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun initView() {

    }

    override fun initClick() {
    }

    override fun showNewsDetail(news: News) {
        // Do nothing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

}