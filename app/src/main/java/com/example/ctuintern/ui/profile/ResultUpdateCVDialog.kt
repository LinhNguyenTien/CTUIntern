package com.example.ctuintern.ui.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.R
import com.example.ctuintern.databinding.EmptyCvDialogBinding
import com.example.ctuintern.databinding.ResultUploadCvDialogBinding

class ResultUpdateCVDialog(context: Context, val isSuccess: Boolean): Dialog(context) {
    private var _binding: ResultUploadCvDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ResultUploadCvDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if(isSuccess) {
                content.text = "Tải lên thành công"
                icon.setBackgroundResource(R.drawable.success)
            }
            else {
                content.text = "Tải lên thất bại"
                icon.setBackgroundResource(R.drawable.fail)
            }
        }
    }
}