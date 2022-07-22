package com.example.demoproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.data.model.ResultsItem
import com.example.demoproject.databinding.RecyclerRowBinding

class UserPagingAdapter(var results : ArrayList<ResultsItem>,var clickListener: OnUserItemClickListener): PagingDataAdapter<ResultsItem,UserPagingAdapter.MyViewHolder>(
    COMPARATOR) {

    fun setUpdatedData(results : ArrayList<ResultsItem>) {
        this.results = results
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: ResultsItem,action: OnUserItemClickListener){

            binding.result = data
            binding.root.setOnClickListener {
                action.onItemClick(data,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = RecyclerRowBinding.inflate(view,parent,false)

        return MyViewHolder(listItemBinding)
    }

    /*override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(results.get(position),clickListener)

    }*/

    override fun getItemCount(): Int {
        return results.size
    }

    interface OnUserItemClickListener{
        fun onItemClick(item: ResultsItem,position: Int)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem.id?.name == newItem.id?.name
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(results.get(position),clickListener)
    }

}