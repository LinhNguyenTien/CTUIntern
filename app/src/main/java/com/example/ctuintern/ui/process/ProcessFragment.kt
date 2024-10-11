package com.example.ctuintern.ui.process

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProcessFragment : MainFragment() {
    override fun initView() {

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
        return inflater.inflate(R.layout.fragment_process, container, false)
    }

}