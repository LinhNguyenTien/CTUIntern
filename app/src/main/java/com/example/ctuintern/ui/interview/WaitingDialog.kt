package com.example.ctuintern.ui.interview

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.ctuintern.databinding.WaitingDialogBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WaitingDialog(context: Context, val time: LocalDateTime): Dialog(context) {
    private var _binding: WaitingDialogBinding? = null
    private val binding get() = _binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = WaitingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss ngày dd-MM-yyyy")
        val formattedTime = time.format(outputFormatter)
        binding.time.text = formattedTime
        binding.close.setOnClickListener { dismiss() }
    }
}