package com.example.ctuintern.ui.classManagement
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Teacher
import com.example.ctuintern.databinding.FragmentClassManagementBinding
import com.example.ctuintern.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ClassManagementFragment : MainFragment() {
    private var _binding: FragmentClassManagementBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClassManagementViewModel by viewModels()
    private lateinit var teacher: Teacher
    override fun initView() {
        val user = getCurrentUser()
        if(user != null && user is Teacher) {
            teacher = user
            binding.profile.text = teacher.userName
            viewModel.initView(teacher.userID)
            viewModel.classes.observe(viewLifecycleOwner, Observer {
                val adapter = ClassAdapter() {
                    // navigate to a detail class fragment
                }
                adapter.setDataSet(it)
                binding.recyclerView.adapter = adapter
            })
        }
    }
    override fun initClick() {
        binding.apply {
            profile.setOnClickListener {
                navigateToFragment(root, R.id.action_classManagementFragment_to_profileFragment)
            }
            review.setOnClickListener {
                navigateToFragment(root, R.id.action_classManagementFragment_to_reviewFragment)
            }
            process.setOnClickListener {
                navigateToFragment(root, R.id.action_classManagementFragment_to_processFragment)
            }
            statistic.setOnClickListener {
                navigateToFragment(root, R.id.action_classManagementFragment_to_statisticFragment)
            }
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
        _binding = FragmentClassManagementBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}