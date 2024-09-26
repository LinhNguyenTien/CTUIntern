package com.example.ctuintern.ui.register

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.RegisterFailDialogBinding

class RegisterFailDialog(context: Context, reason: String): Dialog(context) {
    private var _binding: RegisterFailDialogBinding? = null
    private val binding get() = _binding!!
    private val reason: String = reason
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RegisterFailDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.reason.text = reason
    }
}