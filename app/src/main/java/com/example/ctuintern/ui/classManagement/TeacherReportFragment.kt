package com.example.ctuintern.ui.classManagement

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.ctuintern.R
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.databinding.FragmentTeacherReportBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ulti.fetchFileFromFirebaseStorage
import com.example.ctuintern.ulti.parseCourseFile_DanhGiaGiaoVien
import com.example.ctuintern.ulti.parseCourseFile_PhieuDanhGia
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherReportFragment : MainFragment() {
    private var _binding: FragmentTeacherReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var internProfile: InternProfile
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        internProfile =
            requireArguments().getSerializable("internProfile", InternProfile::class.java)!!
        binding.apply {
            teacherName.text = "Họ và tên: " + getCurrentUser()!!.userName
            studentName.text = "Họ và tên: " + internProfile.student.userName
        }
        if(internProfile.internReport.teacherReportPath != null ||
            internProfile.internReport.teacherReportPath != "") {
            Log.i("teacherReportPath", "not null, ${internProfile.internReport.teacherReportPath.toString()}")
            fetchFileFromFirebaseStorage(
                internProfile.internReport.teacherReportPath.toString(),
                onSuccess = { fileContent ->
                    val teacherReview = parseCourseFile_DanhGiaGiaoVien(fileContent)
                    if (teacherReview != null) {
                        binding.apply {
                            studentName.text = "Họ và tên: " + internProfile.student.userName
                            studentCode.text = "MSSV: " + internProfile.student.studentID
                            I1score.text = teacherReview.i1.toString()
                            I2score.text = teacherReview.i2.toString()
                            II1score.text = teacherReview.ii1.toString()
                            II2score.text = teacherReview.ii2.toString()
                            val scoreCompany = internProfile.internReport.companyScore!! *  0.08
                            val roundedNumber = Math.round(scoreCompany * 10) / 10.0
                            III1score.text = roundedNumber.toString()
                            IVscore.text = teacherReview.iv.toString()
                            val total = teacherReview.i1 + teacherReview.i2 +
                                    teacherReview.ii1 + teacherReview.ii2 +
                                    teacherReview.iv +
                                    (internProfile.internReport.companyScore!!/10 * 0.8)
                            totalScore.text = (Math.round(total * 10) / 10.0).toString()
                            minusScore.text = teacherReview.minus.toString()
                            restScore.text = (Math.round((total - teacherReview.minus) * 10) / 10.0).toString()
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
        else {
            Log.i("teacherReportPath", "it is null")
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
        _binding = FragmentTeacherReportBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }
}