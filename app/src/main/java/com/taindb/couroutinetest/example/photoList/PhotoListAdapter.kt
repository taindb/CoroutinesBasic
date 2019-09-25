package com.taindb.couroutinetest.example.photoList

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.taindb.couroutinetest.R
import com.taindb.couroutinetest.RetroPhoto


class PhotoListAdapter(private val context: Context, private val dataList: List<RetroPhoto>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PhotoListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {

        var txtTitle: TextView = mView.findViewById(R.id.title)
        val coverImage: ImageView = mView.findViewById(R.id.coverImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.photo_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.txtTitle.text = dataList[position].title

        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position].thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
