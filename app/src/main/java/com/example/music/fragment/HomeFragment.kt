package com.example.music.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.music.R
import com.example.music.databinding.FragmentHomeBinding
import com.example.music.models.MyObject
import com.example.music.models.RvAdapter
import com.example.music.models.Song

class HomeFragment : Fragment(), RvAdapter.ClicIterface {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: List<Song>
    private lateinit var song: Song





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check runtime permission to read external storage
        }

        if (MyObject.mediaPlayer!= null){
            binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)
        }else{
            binding.pauseButton2.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }

        if (MyObject.mediaPlayer==null){
            binding.pauseButton2.setImageResource(R.drawable.ic_baseline_play_arrow_24)

        }else{
            binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)

        }


        // Get the external storage/sd card music files list
        val list: MutableList<Song> = requireActivity().musicFiles()

//         Get the sd card music titles list`
        val titles = mutableListOf<String>()
          MyObject.list=list as ArrayList<Song>
        for (music in list) {
            titles.add(music.title)
        }

        // Display external storage music files list on list view
        val rvAdapter = RvAdapter(list, this)
        list.sortByDescending { list.toString() }
        binding.rv.adapter = rvAdapter
        mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(list[0].musicPath), null)
//            binding.rv.setOnClickListener { position->
//                mediaPlayer = MediaPlayer.create(requireContext(),  Uri.parse(list[position].musicPath),null)
//                mediaPlayer.start()
//
//            }
//            binding.rv.setOnLongClickListener { position ->
//                mediaPlayer.pause()
//                true
//            }

//          onDestroy()


           if (MyObject.mediaPlayer!=null){
               Toast.makeText(binding.root.context, "this", Toast.LENGTH_SHORT).show()
               binding.liner.setOnClickListener {
                   findNavController().navigate(R.id.action_homeFragment_to_musisFragment)
               }
           }else{

               binding.liner.setOnClickListener {
                   findNavController().navigate(R.id.action_homeFragment_to_musisFragment)
               }

           }


        return binding.root

    }


    @SuppressLint("MissingSuperCall")
//    override fun onStop() {
//        if (MyObject.mediaPlayer!!.isPlaying){
//            MyObject.mediaPlayer?.stop()
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)



        binding.pauseButton2.setOnClickListener {

                if (MyObject.mediaPlayer!!.isPlaying ) {
                    MyObject.mediaPlayer!!.pause()
                    binding.pauseButton2.setImageResource(R.drawable.ic_baseline_play_arrow_24)

                } else {
                    MyObject.mediaPlayer!!.start()
                    binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)
//                binding.textName.text=MyObject.list[0].title


                }





        }
    }


//    override fun onResume() {
//        onDestroy()
//    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        binding.textName.text=MyObject.list[MyObject.position].author
//          binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)
          binding.textName.text=MyObject.list[MyObject.position].author



          binding.pauseButton2.setOnClickListener {
              if (MyObject.mediaPlayer!!.isPlaying){
                  MyObject.mediaPlayer!!.pause()
                  binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)

              }else{
                  MyObject.mediaPlayer!!.start()
                  binding.pauseButton2.setImageResource(R.drawable.ic_baseline_play_arrow_24)
              }
          }

    }

    override fun clicItem(song: Song, position: Int) {
        if (mediaPlayer != null) {
            mediaPlayer!!.reset()
        }

        MyObject.mediaPlayer?.stop()
        mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(song.musicPath), null)
        MyObject.mediaPlayer = mediaPlayer
        mediaPlayer!!.start()
        binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)





        binding.textName.text =MyObject.list[position].author
        MyObject.namemusic = MyObject.list[position].title


//        rvAdapter= RvAdapter(list,this)
//        if (rvAdapter.list[position].imagePath!=""){
//            val bm=BitmapFactory.decodeFile(rvAdapter.list[position].imagePath)
//            binding.imageMenu.setImageBitmap(bm)
//        }
////        MyObject.namemusic=song.title
//        MyObject.textname=song.author





//            else{
//                mediaPlayer.start()
//                binding.pauseButton2.setImageResource(R.drawable.ic_baseline_pause_24)
//
//            }


    }


}




private operator fun <E> MutableList<E>.get(position: View?): E {
    TODO("Not yet implemented")
}


@SuppressLint("Range")
fun Context.musicFiles(): MutableList<Song> {

    // Initialize an empty mutable list of music
    val list: MutableList<Song> = mutableListOf()

    // Get the external storage media store audio uri
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    //val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

    // IS_MUSIC : Non-zero if the audio file is music
    val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

    // Sort the musics
    val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
    //val sortOrder = MediaStore.Audio.Media.TITLE + " DESC"

    // Query the external storage for music files

    val cursor: Cursor? = this.contentResolver.query(
        uri, // Uri
        null, // Projection
        selection, // Selection
        null, // Selection arguments
        sortOrder // Sort order
    )

    // If query result is not empty
    if (cursor != null && cursor.moveToFirst()) {
        val id: Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val title: Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val imageId: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)
        val authorId: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

        // Now loop through the music files
        do {
            val audioId: Long = cursor.getLong(id)
            val audioTitle: String = cursor.getString(title)
            var imagePath: String = ""
            if (imageId != -1) {
                imagePath = cursor.getString(imageId)
            }
            val musicPath: String =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val artist = cursor.getString(authorId)

            // Add the current music to the list
            list.add(Song(audioId, audioTitle, imagePath, musicPath, artist))
        } while (cursor.moveToNext())
    }

    return list


}



