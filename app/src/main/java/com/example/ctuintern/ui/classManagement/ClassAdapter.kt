package com.example.ctuintern.ui.classManagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.data.model.Class
import com.example.ctuintern.databinding.ClassItemBinding

class ClassAdapter(
    private val showDetail:(Class) -> Unit
): RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {
    private lateinit var classes: List<Class>

    fun setDataSet(classes: List<Class>) {
        this.classes = classes
    }

    class ClassViewHolder(val binding: ClassItemBinding) : RecyclerView.ViewHolder(binding.root) {
         fun bind(myClass: Class, showDetail: (Class) -> Unit) {
             binding.className.text = myClass.className
             binding.classCode.text = myClass.classID
             binding.root.apply {
                 setOnClickListener {
                     showDetail(myClass)
                 }
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(
            ClassItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val myClass = classes[position]
        holder.bind(myClass, showDetail)
    }
}