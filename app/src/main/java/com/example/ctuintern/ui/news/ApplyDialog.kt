package com.example.ctuintern.ui.news

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Html
import androidx.core.content.ContextCompat.getString
import com.example.ctuintern.R
import com.example.ctuintern.databinding.ApplyDialogBinding

class ApplyDialog(context: Context, val switchToApplyFragment:()->Unit): Dialog(context) {
    private var _binding: ApplyDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ApplyDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.reason.text = Html.fromHtml(
            getString(context, R.string.applyNewsContent),
            Html.FROM_HTML_MODE_LEGACY
        )
        binding.closeBtn.setOnClickListener {
            dismiss()
        }
        binding.switchApplyBtn.setOnClickListener {
            switchToApplyFragment()
            dismiss()
        }
    }
}