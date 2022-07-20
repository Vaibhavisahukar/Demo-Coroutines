package com.example.demoproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoproject.R
import com.example.demoproject.data.model.ResultsItem
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(var results : ArrayList<ResultsItem>,var clickListener: OnUserItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    fun setUpdatedData(results : ArrayList<ResultsItem>) {
        this.results = results
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val image : ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.nameTv)

        fun bind(data: ResultsItem,action: OnUserItemClickListener){
            name.text = data.name?.first +" "+ data.name?.last

            val url = data.picture?.large

            Picasso.get()
                .load(url)
                .into(image)

            itemView.setOnClickListener {
                action.onItemClick(data,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(results.get(position),clickListener)

    }

    override fun getItemCount(): Int {
        return results.size
    }

    interface OnUserItemClickListener{
        fun onItemClick(item: ResultsItem,position: Int)
    }
}
