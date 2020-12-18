package com.anubhab.das.marveltask.ui.marvel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anubhab.das.marveltask.R
import com.anubhab.das.marveltask.data.room.Marvel
import com.anubhab.das.marveltask.ui.marvel.listMarvel.MarvelListViewModel
import com.bumptech.glide.Glide


class MarvelAdapter: RecyclerView.Adapter<MarvelAdapter.MarvelCharHolder>() {

    private var marvels: List<Marvel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.marvel_char_item, parent, false)
        return MarvelCharHolder(view)
    }

    override fun getItemCount(): Int {
        return marvels.size
    }

    override fun onBindViewHolder(holder: MarvelCharHolder, position: Int) {
        val currentItem = marvels[position];
        holder.name.text = currentItem.name;
        holder.desc.text = currentItem.desc;
        Glide.with(holder.image.context).load(currentItem.img).centerCrop().into(holder.image);
    }

    fun setMarvelList(marvel: List<Marvel>) {
        this.marvels = marvel
        notifyDataSetChanged()
    }

    fun getMarvelAt(position: Int): Marvel {
        return marvels[position]
    }

    class MarvelCharHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.marvel_image)
        val name: TextView = itemView.findViewById(R.id.marvel_name)
        val desc: TextView = itemView.findViewById(R.id.marvel_description)
    }
}



