package org.d3if0119.pomodoroappnew.ui.tasks

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if0119.pomodoroappnew.databinding.ListTasksBinding
import org.d3if0119.pomodoroappnew.db.TaskEntity

class TasksAdapter(private val onDelete: (TaskEntity)->Unit) : ListAdapter<TaskEntity, TasksAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<TaskEntity>(){
                override fun areItemsTheSame(oldData: TaskEntity, newData: TaskEntity): Boolean {
                    return oldData.id == newData.id
                }

                override fun areContentsTheSame(oldData: TaskEntity, newData: TaskEntity): Boolean {
                    return oldData == newData
                }
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListTasksBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind((getItem(position)))
    }
    inner class ViewHolder(
        private val binding: ListTasksBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskEntity) {
            binding.list.text = item.name

            binding.deleteButton.setOnClickListener {
                onDelete(item)
            }
        }
    }

}