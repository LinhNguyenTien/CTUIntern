package com.example.ctuintern.ui.classManagement

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.ctuintern.databinding.NotAvailableDialogBinding

class NotAvailableDialog(context: Context, content: String): Dialog(context) {
    private var _binding: NotAvailableDialogBinding? = null
    private val binding get() = _binding!!
    private val content: String = content
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = NotAvailableDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            close.setOnClickListener {
                dismiss()
            }
            reason.text = content
        }
    }
}