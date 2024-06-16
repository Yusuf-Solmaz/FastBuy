package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer.ui.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusuf.yusuf_mucahit_solmaz_final.data.mapper.formatDate
import com.yusuf.yusuf_mucahit_solmaz_final.data.remote.responses.product.Review
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ItemCommentsBinding

class CommentsAdapter(private val comments: ArrayList<Review>) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    class CommentsViewHolder(val binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val itemBinding = ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.binding.apply {
            ratingBar.rating = comments[position].rating.toFloat()
            name.text = comments[position].reviewerName
            email.text = comments[position].reviewerEmail
            comment.text = comments[position].comment
            date.text = comments[position].date.formatDate()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateComments(newComments: List<Review>) {
        comments.clear()
        comments.addAll(newComments)
        notifyDataSetChanged()
    }
}
