package com.example.ctuintern.ui.classManagement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.data.model.InternProfile
import com.example.ctuintern.data.model.Student
import com.example.ctuintern.databinding.StudentListItemBinding

class StudentAdapter(private val showStudentDetail:(InternProfile) -> Unit): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private lateinit var students: List<InternProfile>

    fun setDataset(students: List<InternProfile>) {
        this.students = students
    }

    class StudentViewHolder(private val binding: StudentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: InternProfile, position: Int, showStudentDetail:(InternProfile) -> Unit) {
            binding.stt.text = position.toString()
            binding.studentCode.text = student.student.studentID
            binding.studentName.text = student.student.userName

            binding.root.setOnClickListener {
                showStudentDetail(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            StudentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student, position, showStudentDetail)
    }
}