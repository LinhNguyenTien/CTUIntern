package com.example.ctuintern.ui.classManagement
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentClassDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.ui.intern.TaskAdapter

class ClassDetailFragment : MainFragment() {
    private var _binding: FragmentClassDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClassManagementViewModel by viewModels()
    private val args: ClassDetailFragmentArgs by navArgs()
    private val myClass: Class = args.`class`
    override fun initView() {
        viewModel.initDetailClassView(myClass.classID)
        viewModel.students.observe(viewLifecycleOwner, Observer {
            binding.apply {
                className.text = "Lớp: ${myClass.className}"
                classCode.text = "Mã lớp: ${myClass.classCode}"
                number.text = "Sĩ số: ${it.size}"

                val adapter = StudentAdapter(
                    showStudentDetail = {
                        navigateToFragment(root, ClassDetailFragmentDirections.actionClassDetailFragmentToStudentDetailFragment(it))
                    }
                )
                adapter.setDataset(it)
                binding.studentRecycler.adapter = adapter
            }
        })

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            val adapter = TaskAdapter(
                showDetail = {
                    // navigate to task detail fragment
                }
            )
            adapter.muteTaskState()
            adapter.setDataSet(it)
            binding.taskRecycler.adapter = adapter
        })
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
        _binding = FragmentClassDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}