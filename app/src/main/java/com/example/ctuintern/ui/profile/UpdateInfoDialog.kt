package com.example.ctuintern.ui.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.UpdateInfoDialogBinding
import com.example.ctuintern.databinding.UploadCvDialogBinding

class UpdateInfoDialog(
    context: Context,
    val updateCV:()->Unit,
    val updateProfile:(String)->Unit
): Dialog(context) {
    private var _binding: UpdateInfoDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = UpdateInfoDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            updateCV.setOnClickListener {
                updateCV()
            }
            close.setOnClickListener {
                dismiss()
            }
            updateButton.setOnClickListener {
                val newPhone = binding.phone.text.toString().trim()
                if(newPhone.isNotEmpty()) {
                    updateProfile(binding.phone.text.toString())
                    dismiss()
                }
                else {
                    dismiss()
                }
            }
        }
    }
}