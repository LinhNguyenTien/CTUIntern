package com.example.ctuintern.ui.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.LoadingDialogBinding
import com.example.ctuintern.databinding.LoginFailDialogBinding

class LoadingDialog(context: Context): Dialog(context) {
    private var _binding: LoadingDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = LoadingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}