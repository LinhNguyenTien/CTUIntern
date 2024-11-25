package com.example.ctuintern.ui.classManagement
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentClassDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.ui.intern.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassDetailFragment : MainFragment() {
    private var _binding: FragmentClassDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ClassManagementViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private lateinit var myClass: Class
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        try {
            myClass = requireArguments().getSerializable("myClass", Class::class.java)!!
            if(myClass != null) {
                Log.i("class detail", "myclass is not null, $myClass")
            }
            else {
                Log.i("class detail", "myclass is null")
            }
            viewModel.initDetailClassView(myClass!!.classID)
            viewModel.students.observe(viewLifecycleOwner, Observer {
                binding.apply {
                    className.text = "Lớp: ${myClass.className}"
                    classCode.text = "Mã lớp: ${myClass.classCode}"
                    number.text = "Sĩ số: ${it.size} sinh viên"
                    val adapter = StudentAdapter(
                        showStudentDetail = {
                            navigateToFragment(
                                root,
                                R.id.action_classDetailFragment_to_studentDetailFragment,
                                bundleOf("student" to it)
                            )
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
        catch(e: Exception) {
            Log.i("ClassManagementFragment", "initView: myClass is null")
            Log.e("ClassManagementFragment", "initView: ", e)
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
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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