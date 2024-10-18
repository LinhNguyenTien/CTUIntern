package com.example.ctuintern.ui.classManagement
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentStudentDetailBinding
import com.example.ctuintern.ui.main.MainFragment

class StudentDetailFragment : MainFragment() {
    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!
    override fun initView() {
        binding.apply {
            studentName.text
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
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}