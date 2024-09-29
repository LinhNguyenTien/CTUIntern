package com.example.ctuintern.ui.news

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentCompanyInformationBinding
import com.example.ctuintern.databinding.FragmentNewsDetailBinding
import com.example.ctuintern.ui.main.MainFragment

class CompanyInformationFragment(val news: News) : MainFragment() {
    private var _binding: FragmentCompanyInformationBinding? = null
    private val binding get() = _binding!!

    override fun initView() {
        binding.address.text = news.employer!!.address
        binding.website.text = news.employer!!.websiteAddress
        binding.size.text = news.employer!!.size + " Người"
        binding.field.text = news.employer!!.field
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
        _binding = FragmentCompanyInformationBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }
}