package com.example.ctuintern.ui.classManagement

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentTaskListReportBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ulti.CourseInfo
import com.example.ctuintern.ulti.fetchFileFromFirebaseStorage
import com.example.ctuintern.ulti.parseCourseFile_PhieuGiaoViec
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListReportFragment : MainFragment() {
    private var _binding: FragmentTaskListReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var internProfile: InternProfile
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        internProfile =
            requireArguments().getSerializable("internProfile", InternProfile::class.java)!!
        if(internProfile != null) {
            if(internProfile.internReport.taskListReportPath != null ||
                internProfile.internReport.taskListReportPath != "") {
                Log.i("taskListReportPath", "not null, ${internProfile.internReport.taskListReportPath.toString()}")
                fetchFileFromFirebaseStorage(
                    internProfile.internReport.taskListReportPath.toString(),
                    onSuccess = { fileContent ->
                        val courseInfo = parseCourseFile_PhieuGiaoViec(fileContent)
                        if (courseInfo != null) {
                            binding.apply {
                                studentName.text = "Họ và tên: " + internProfile.student.userName
                                studentCode.text = "MSSV: " + internProfile.student.studentID
                                companyName.text = "Đơn vị: " + internProfile.news?.employer?.userName
                                if (courseInfo != null) {
                                    companyTutorial.text = "Cán bộ hướng dẫn: " + courseInfo!!.trainerInfo.name
                                    fromDay.text = "Từ ngày: " + courseInfo.trainerInfo.startDate
                                    toDay.text = "Đến ngày: " + courseInfo.trainerInfo.endDate
                                    mentorName.text = "Cán bộ hướng dẫn: " + courseInfo!!.trainerInfo.name
                                    mentorEmail.text = "Email: " + courseInfo!!.trainerInfo.email
                                    mentorPhone.text = "Số điện thoại: " + courseInfo!!.trainerInfo.phone
                                    val adapter = WeeklyReviewAdapter()
                                    adapter.muteReview()
                                    adapter.setDataset(courseInfo.weeklyWorks)
                                    taskReview.adapter = adapter
                                }
                            }
                        } else {
                            Log.i("TaskList","Failed to parse the course file.")
                        }
                    },
                    onError = { error ->
                        println("Error fetching file: ${error.message}")
                    }
                )
            }
        }
    }

    override fun initClick() {
        binding.back.setOnClickListener {
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
        _binding = FragmentTaskListReportBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}