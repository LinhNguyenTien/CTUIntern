package com.example.ctuintern.ui.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.ctuintern.databinding.EmptyCvDialogBinding
import com.example.ctuintern.databinding.ShowFullScreenDialogBinding
import com.example.ctuintern.databinding.UploadCvDialogBinding

class EmptyCVDialog(context: Context, private val addCV:()->Unit): Dialog(context) {
    private var _binding: EmptyCvDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = EmptyCvDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            close.setOnClickListener { dismiss() }
            add.setOnClickListener {
                dismiss()
                addCV()
            }
        }
    }
}
