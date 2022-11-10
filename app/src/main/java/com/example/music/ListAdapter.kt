//package com.example.music
//
//import android.content.Context
//import android.graphics.BitmapFactory
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import com.example.music.fragment.HomeFragment
//import com.example.music.models.Song
//
//class ListAdapter(context: Context, val list: List<Song>)
//    : ArrayAdapter<Song>(context, R.layout.item_list, list){
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//
//        var itemView: View
//        if (convertView==null){
//            itemView = LayoutInflater.from(parent.context).inflate(
//                R.layout.item_list,
//                parent,
//                false
//            )
//        }else{
//            itemView = convertView
//        }
//        itemView.text_title.text = list[position].title
//        val bm = BitmapFactory.decodeFile(list[position].imagePath)
//        itemView.item_image.setImageBitmap(bm)
//        return itemView
//    }
//
//}