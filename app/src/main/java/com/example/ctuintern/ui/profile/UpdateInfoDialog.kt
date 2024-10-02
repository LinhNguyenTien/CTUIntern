package com.example.ctuintern.ui.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.UploadCvDialogBinding

class UpdateInfoDialog(context: Context): Dialog(context) {
    private var _binding: UploadCvDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = UploadCvDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            uploadArea.setOnClickListener {

                dismiss()
            }
            close.setOnClickListener {
                dismiss()
            }
        }
    }
}