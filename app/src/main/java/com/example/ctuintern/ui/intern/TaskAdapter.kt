package com.example.ctuintern.ui.intern

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctuintern.data.model.Task
import com.example.ctuintern.databinding.TaskItemBinding

class TaskAdapter(val showDetail:(Task)->Unit): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private lateinit var tasks: List<Task>
    private var hideTaskState = true
    fun setDataSet(tasks: List<Task>) {
        this.tasks = tasks
    }

    fun muteTaskState() {
        hideTaskState = false
    }

    class TaskViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, muteTaskState: Boolean, showDetail: (Task) -> Unit) {
            binding.apply {
                taskTitle.text = task.title
                taskContent.text = task.content
                taskExpiredDay.text = task.expiredDay
                taskState.text = if(task.detail != null) "Đã nộp" else "Chưa nộp"
                taskState.visibility = if(muteTaskState) VISIBLE else GONE
                root.setOnClickListener {
                    showDetail(task)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return tasks.size?:0
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        if(task != null) {
            holder.bind(task, hideTaskState, showDetail)
        }
    }
}