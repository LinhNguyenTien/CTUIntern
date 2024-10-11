package com.example.ctuintern.ui.intern

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.R
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.FragmentApplyBinding
import com.example.ctuintern.databinding.FragmentInternBinding
import com.example.ctuintern.ui.interview.WaitingDialog
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ui.profile.ProfileFragment
import com.example.ctuintern.ui.profile.ResultUpdateCVDialog
import com.example.ctuintern.ulti.RecordType
import com.example.ctuintern.ulti.UploadState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InternFragment : MainFragment() {
    private var _binding: FragmentInternBinding? = null
    private val binding get() = _binding!!
    private lateinit var student: Student
    private lateinit var internProfile: InternProfile
    private val viewModel: InternViewModel by viewModels()

    override fun initView() {
        Log.i("intern", "run initView")
        val user = getCurrentUser()
        Log.i("intern", "call getCurrentUser")
        if(user != null && user is Student) {
            student = user
            Log.i("intern", "user is student, studentID: ${student.userID}")
            viewModel.getInternProfile(student.userID)
            viewModel.internProfile.observe(viewLifecycleOwner, Observer {
                internProfile = it
                Log.i("intern", "get intern profile success")
                setUpInformation(internProfile)
            })
            viewModel.uploadState.observe(viewLifecycleOwner, Observer {
                if(it == UploadState.SUCCESS) {
                    binding.check.setImageResource(R.drawable.check)
                    viewModel.resetUploadState()
                }
                else if(it == UploadState.FAIL){
                    showRoundedDialog(
                        ResultUpdateCVDialog(
                            context = requireContext(),
                            isSuccess = false
                        )
                    )
                    viewModel.resetUploadState()
                }
                else { /*do nothing for UPLOADING*/ }
            })
        }
    }

    private fun showRoundedDialog(dialog: Dialog) {
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                resources.getColor(android.R.color.transparent)
            )
        )
        dialog.show()
    }

    private fun setUpInformation(ip: InternProfile) {
        Log.i("intern", "run setUpInformation")
        binding.apply {
            profile.text = student.userName

            companyName.text = ip.news.employer?.userName
            companyPhone.text = ip.news.employer?.phone
            companyAddress.text = ip.news.location

            teacherName.text = ip.student.classCTU.teacher.userName
            teacherPhone.text = ip.student.classCTU.teacher.phone
            teacherEmail.text = ip.student.classCTU.teacher.email

            viewModel.getTasks(student.userID) {
                val adapter = TaskAdapter() { task ->
                    navigateToFragment(binding.root, InternFragmentDirections.actionInternFragmentToTaskDetailFragment(task))
                }
                adapter.setDataSet(it)
                recycleView.adapter = adapter
            }

            uploadIcon.setOnClickListener {
                openFilePicker()
            }

            internProfile.internReport.studentReportPath.let {
                if(it?.isNotEmpty() == true) {
                    check.setImageResource(R.drawable.check)
                }
            }
        }
        Log.i("intern", "end setUpInformation")
    }

    override fun initClick() {

    }

    override fun showNewsDetail(news: News) {

    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.uploadRecordToFBS(student, internProfile.internReport.reportID, uri) {
                    internProfile.internReport.studentReportPath = it
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInternBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

    companion object {
        private const val PICK_FILE_REQUEST_CODE = 1
    }
}