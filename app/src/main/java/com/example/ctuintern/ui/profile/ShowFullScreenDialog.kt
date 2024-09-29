package com.example.ctuintern.ui.profile

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import com.bumptech.glide.Glide
import com.example.ctuintern.databinding.ShowFullScreenDialogBinding

class ShowFullScreenDialog(val context: Context, val imageURL: String): Dialog(context) {
    private var _binding: ShowFullScreenDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ShowFullScreenDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Glide.with(context)
                .load(imageURL)
                .into(image)

            download.setOnClickListener {
                val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(Uri.parse(imageURL))

                request.setTitle("Downloading file")
                request.setDescription("Downloading file")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "achievement")

                // Enqueue the request
                downloadManager.enqueue(request)
            }

            close.setOnClickListener {
                dismiss()
            }
        }
    }
}