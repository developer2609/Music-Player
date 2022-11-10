package com.example.music.models

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.databinding.ItemListBinding


class RvAdapter (var list: List<Song>,var clicbelgi:ClicIterface) : RecyclerView.Adapter<RvAdapter.VP_Vh>() {

     inner class VP_Vh(var itemListBinding: ItemListBinding):
         RecyclerView.ViewHolder(itemListBinding.root) {
         fun onBind(song: Song,position: Int) {

             itemListBinding.textTitle.text=song.title
             itemListBinding.textAuthor.text=song.author
               MyObject.position=position
             if (song.imagePath!=""){
                 val bm=BitmapFactory.decodeFile(list[position].imagePath)
                 itemListBinding.itemImage.setImageBitmap(bm)
             }


             itemListBinding.root.setOnClickListener {
                 clicbelgi.clicItem(song,position)
             }



         }
     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VP_Vh {
         return VP_Vh(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))


     }

     override fun onBindViewHolder(holder: VP_Vh, position: Int) {
         holder.onBind(list[position],position)
     }

     override fun getItemCount(): Int = list.size

    interface ClicIterface{
        fun clicItem(song: Song,position: Int)
    }



 }