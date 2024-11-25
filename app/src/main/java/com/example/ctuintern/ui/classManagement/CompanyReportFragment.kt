package com.example.ctuintern.ui.classManagement

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentCompanyReportBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ulti.fetchFileFromFirebaseStorage
import com.example.ctuintern.ulti.parseCourseFile_PhieuDanhGia
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyReportFragment : MainFragment() {
    private var _binding: FragmentCompanyReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var internProfile: InternProfile
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        internProfile =
            requireArguments().getSerializable("internProfile", InternProfile::class.java)!!
        if(internProfile != null) {
            if(internProfile.internReport.reviewReportPath != null ||
                internProfile.internReport.reviewReportPath != "") {
                Log.i("taskListReportPath", "not null, ${internProfile.internReport.reviewReportPath.toString()}")
                fetchFileFromFirebaseStorage(
                    internProfile.internReport.reviewReportPath.toString(),
                    onSuccess = { fileContent ->
                        val companyReview = parseCourseFile_PhieuDanhGia(fileContent)
                        if (companyReview != null) {
                            binding.apply {
                                studentName.text = "Họ và tên: " + internProfile.student.userName
                                studentCode.text = "MSSV: " + internProfile.student.studentID
                                fromDay.text = "Từ ngày: " + companyReview.fromDay
                                toDay.text = "Đến ngày: " + companyReview.toDay
                                I1score.text = companyReview.i1.toString()
                                I2score.text = companyReview.i2.toString()
                                I3score.text = companyReview.i3.toString()
                                I4score.text = companyReview.i4.toString()
                                II1score.text = companyReview.ii1.toString()
                                II2score.text = companyReview.ii2.toString()
                                II3score.text = companyReview.ii3.toString()
                                III1score.text = companyReview.iii1.toString()
                                III2score.text = companyReview.iii2.toString()
                                III3score.text = companyReview.iii3.toString()
                                total.text = companyReview.total.toString()
                                otherStudentReview.text = "Nhận xét khác về sinh viên: " + companyReview.otherReview
                                when(companyReview.option) {
                                    1 -> check1.isChecked = true
                                    2 -> check2.isChecked = true
                                    3 -> check3.isChecked = true
                                    4 -> check4.isChecked = true
                                    else -> check5.isChecked = true
                                }
                                otherReview.text = "Đề xuất góp ý về chương trình đào tạo: " + companyReview.otherOption
                                mentorName.text = "Cán bộ hướng dẫn: " + companyReview.mentorName
                                mentorEmail.text = "Email: " + companyReview.email
                                mentorPhone.text = "Điện thoại: " + companyReview.phone
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
        _binding = FragmentCompanyReportBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

}