package org.d3if0119.pomodoroappnew.ui.recomendation

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0119.pomodoroappnew.R
import org.d3if0119.pomodoroappnew.databinding.ListBookBinding
import org.d3if0119.pomodoroappnew.model.Book
import org.d3if0119.pomodoroappnew.network.BookApi

class RecomendationAdapter: RecyclerView.Adapter<RecomendationAdapter.ViewHolder>(){
    private val data = mutableListOf<Book>()

    fun updateData(newData: List<Book>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book) = with(binding){
            namaTextView.text = book.judul
            Glide.with(imageView.context).load(BookApi.getBookUrl(book.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListBookBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}