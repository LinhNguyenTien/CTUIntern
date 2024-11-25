package com.example.ctuintern.ui.intern

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.ctuintern.R
import com.example.ctuintern.data.model.News
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.data.model.TaskDetail
import com.example.ctuintern.databinding.FragmentInternBinding
import com.example.ctuintern.databinding.FragmentTaskDetailBinding
import com.example.ctuintern.ui.main.MainFragment
import com.example.ctuintern.ui.profile.ProfileFragment
import com.example.ctuintern.ui.profile.ResultUpdateCVDialog
import com.example.ctuintern.ui.profile.UploadState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : MainFragment() {
    private val viewModel: InternViewModel by viewModels()
    private val args: TaskDetailFragmentArgs by navArgs()
    private val task: Task get() = args.task
    private var _binding: FragmentTaskDetailBinding? = null
    private lateinit var taskDetail: TaskDetail
    private val binding get() = _binding!!
    override fun initView() {
        binding.apply {
            title.text = task.title
            content.text = task.content
            time.text = task.expiredDay
            viewModel.getTaskDetail(getCurrentUser()!!.userID, task.taskID.toString())
            viewModel.taskDetail.observe(viewLifecycleOwner, Observer {
                taskDetail = it
                state.text = if(it.path!=null) "Đã nộp" else "Chưa nộp"
                score.text = if(it.score!=null) it.score.toInt().toString() + "/100" else "Chưa chấm"
                submit.text = if(it.path!=null) "Nộp lại" else "Tải lên"
            })
            viewModel.submitTaskState.observe(viewLifecycleOwner, Observer {
                var resultUpdateCVDialog: ResultUpdateCVDialog? = null
                if(it.equals(UploadState.SUCCESS)) {
                    state.text = "Đã nộp"
                    submit.text = "Nộp lại"
                    resultUpdateCVDialog = ResultUpdateCVDialog(requireContext(), true)
                }
                else if(it.equals(UploadState.FAIL)) {
                    resultUpdateCVDialog = ResultUpdateCVDialog(requireContext(), false)
                }
                else {
                    // do nothing
                }
                // Show the dialog
                if (resultUpdateCVDialog != null) {
                    showRoundedDialog(resultUpdateCVDialog)
                    viewModel.resetUploadState()
                }
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
    override fun initClick() {
        binding.apply {
            submit.setOnClickListener {
                openFilePicker()
            }
            backArrow.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun showNewsDetail(news: News) {

    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                viewModel.submitTaskToFBS(getCurrentUser()!!.userID, task.taskID.toString(), uri) {
                    newPath -> taskDetail.path = newPath
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
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        initView()
        initClick()
        return binding.root
    }

    companion object {
        const val PICK_FILE_REQUEST_CODE = 1
    }

}