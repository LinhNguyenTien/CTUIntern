package com.example.ctuintern.ui.classManagement
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentStudentDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import java.util.UUID

class StudentDetailFragment : MainFragment() {
    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: StudentDetailFragmentArgs by navArgs()
    private val internProfile: InternProfile = args.internProfile

    override fun initView() {
        binding.apply {
            studentName.text = "Họ và Tên: ${internProfile.student.userName}"
            studentCode.text = "MSSV: ${internProfile.student.studentID}"
            studentPhone.text = "Số điện thoại: ${internProfile.student.phone}"

            companyName.text = "Đơn vị: ${internProfile.news?.employer?.userName}"
            companyAddress.text = "Đơn vị: ${internProfile.news?.employer?.address}"
            companyPhone.text = "Số điện thoại: ${internProfile.news?.employer?.phone}"

            companyScore.text = if(internProfile.internReport.companyReviewPath != null)
            {
                internProfile.internReport.companyScore.toString()
            }
            else {
                "N/A"
            }
            teacherScore.text = if(internProfile.internReport.teacherReportPath != null)
            {
                internProfile.internReport.teacherReviewScore.toString()
            }
            else {
                "N/A"
            }

            if(checkFormEnough(internProfile)) {
                val companyScore = internProfile.internReport.companyScore?:0.0
                val teacherScore = internProfile.internReport.teacherReviewScore?:0.0
                val total = companyScore + teacherScore
                totalScore.text = total.toString()
                tenPointScale.text = (total/10.0).toString()
                fourPointScale.text = calculateFourPointScale(total/10.0).toString()
                charPointScale.text = calculateCharPointScale(total)
                binding.broadScore.visibility = VISIBLE
            }
            else {
                binding.notReadyForm.visibility = VISIBLE
            }

        }
    }

    private fun checkFormEnough(internProfile: InternProfile): Boolean {
        return (internProfile.internReport.taskListReportPath != null &&
                internProfile.internReport.checkListReportPath != null &&
                internProfile.internReport.reviewReportPath != null &&
                internProfile.internReport.studentReportPath != null &&
                internProfile.internReport.teacherReportPath != null &&
                internProfile.internReport.companyScore != null &&
                internProfile.internReport.teacherReviewScore != null &&
                internProfile.internReport.companyReviewPath != null)
    }

    private fun calculateFourPointScale(tenPointScale: Double): Double {
        return when(tenPointScale) {
            in 9.0..10.0 -> 4.0;
            in 8.0..8.9 -> 3.5;
            in 7.0..7.9 -> 3.0;
            in 6.5..6.9 -> 2.5;
            in 5.5..6.4 -> 2.0;
            in 5.0..5.4 -> 1.5;
            in 4.0..4.9 -> 1.0;
            else -> 0.0;
        }
    }

    private fun calculateCharPointScale(fourPointScale: Double): CharSequence? {
        return when(fourPointScale) {
            4.0 -> "A"
            3.5 -> "B+"
            3.0 -> "B"
            2.5 -> "C+"
            2.0 -> "C"
            1.5 -> "D+"
            1.0 -> "D"
            else -> "F"
        }
    }

    override fun initClick() {
        binding.apply {
            // Phieu giao viec
            if(internProfile.internReport.taskListReportPath != null) {
                checkTaskList.setImageResource(R.drawable.open)
                checkTaskList.setOnClickListener {
                    //navigateToFragment()
                }
            }
            else {
                checkTaskList.setImageResource(R.drawable.not_found)
                checkTaskList.setOnClickListener {
                    showRoundedDialog(
                        NotAvailableDialog(
                            requireContext(),
                            "Phiếu giao việc chưa được cung cấp bởi nhà tuyển dụng"
                        )
                    )
                }
            }
            // Phieu theo doi
            if(internProfile.internReport.checkListReportPath != null) {
                checkCheckList.setImageResource(R.drawable.open)
                checkCheckList.setOnClickListener {
                    //navigateToFragment()
                }
            }
            else {
                checkCheckList.setImageResource(R.drawable.not_found)
                checkCheckList.setOnClickListener {
                    showRoundedDialog(
                        NotAvailableDialog(
                            requireContext(),
                            "Phiếu theo dõi chưa được cung cấp bởi nhà tuyển dụng"
                        )
                    )
                }
            }
            // Phieu danh gia cong ty
            if(internProfile.internReport.reviewReportPath != null) {
                checkCompanyReview.setImageResource(R.drawable.open)
                checkCompanyReview.setOnClickListener {
                    //navigateToFragment()
                }
            }
            else {
                checkCompanyReview.setImageResource(R.drawable.not_found)
                checkCompanyReview.setOnClickListener {
                    showRoundedDialog(
                        NotAvailableDialog(
                            requireContext(),
                            "Phiếu đánh giá chưa được cung cấp bởi nhà tuyển dụng"
                        )
                    )
                }
            }
            // Phieu danh gia giao vien
            checkTeacherReview.setOnClickListener {
                // navigate to teacher review
            }

            checkStudentReport.setOnClickListener {
                if(internProfile.internReport.studentReportPath != null &&
                    internProfile.internReport.studentReportPath!!.isNotEmpty()) {
                    downloadStudentReport(
                        internProfile.internReport.studentReportPath?:"",
                        "Bao_cao_sinh_vien_${UUID.randomUUID()}.pdf"
                    )
                }
                else {
                    showRoundedDialog(
                        NotAvailableDialog(
                            requireContext(),
                            "Báo cáo chưa được cung cấp bởi sinh viên"
                        )
                    )
                }
            }
        }
    }

    private fun downloadStudentReport(fileUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(fileUrl))
            .setTitle("Downloading $fileName")
            .setDescription("Please wait...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
    }

    fun setEventForm() {

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

    private fun showRoundedDialog(dialog: Dialog) {
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        dialog.show()
    }
}