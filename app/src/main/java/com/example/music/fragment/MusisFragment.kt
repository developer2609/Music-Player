package com.example.music.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.music.R
import com.example.music.databinding.FragmentMusisBinding
import com.example.music.models.MyObject
import com.example.music.models.MyObject.index
import com.example.music.models.MyObject.list
import com.example.music.models.MyObject.mediaPlayer
import com.example.music.models.MyObject.position
import com.example.music.models.RvAdapter
import com.example.music.models.Song
import java.lang.Exception
import javax.security.auth.Destroyable


class MusisFragment : Fragment(),RvAdapter.ClicIterface {
    lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var list: ArrayList<Song>
    private lateinit var binding: FragmentMusisBinding
    private lateinit var rvAdapter: RvAdapter
     var position2:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (mediaPlayer!=null){
//            binding.textName.text=song.author
//            binding.textName.text=song.title
//        }else{
//            Toast.makeText(binding.root.context, "save", Toast.LENGTH_SHORT).show()
//        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentMusisBinding.inflate(layoutInflater)
//        val animation= AnimationUtils.loadAnimation(binding.root.context,R.anim.animation)
//        binding.root.startAnimation(animation)



        val list: MutableList<Song> = requireActivity().musicFiles()

//        list=ArrayList()




        binding.seekbar.progress=0
          binding.seekbar.max=MyObject.mediaPlayer!!.duration
        binding.textTime.text= millisecondToTimer(MyObject.mediaPlayer?.duration!!.toLong())
        binding.artistName.text=MyObject.list[position2].title
        binding.musicName.text=MyObject.list[MyObject.position].author



                rvAdapter= RvAdapter(list,this)


        binding.listMusic.setOnClickListener {

            findNavController().popBackStack()
        }



            if (MyObject.mediaPlayer!!.isPlaying){
                binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
            }else{
                binding.pauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
//        binding.speed.setOnClickListener {
//            if (MyObject.mediaPlayer!!.playbackParams.speed == 1.0f) {
//                MyObject.mediaPlayer!!.playbackParams = MyObject.mediaPlayer!!.playbackParams.setSpeed(2.0f)
//            }else{
//                MyObject.mediaPlayer!!.playbackParams=MyObject.mediaPlayer!!.playbackParams.setSpeed(1.0f)
//            }
//        }

        binding.pauseButton.setOnClickListener {
             if (MyObject.mediaPlayer!!.isPlaying){
              MyObject.mediaPlayer!!.pause()
              binding.pauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
          }else{
              MyObject.mediaPlayer!!.start()
              binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
          }

        }








        binding.speedBack.setOnClickListener {
            MyObject.mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.minus(10000))
        }

        binding.speedNext.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.plus(10000))
        }


//           if (mediaPlayer!!.isPlaying){
//               mediaPlayer!!.stop()
//
//               position++
//               MyObject.mediaPlayer= MediaPlayer.create(binding.root.context, Uri.parse(list[MyObject.list.size].musicPath))
//           }
//
        binding.nextButton.setOnClickListener {

            Toast.makeText(binding.root.context, "this", Toast.LENGTH_SHORT).show()
          if (MyObject.position>=list.size-1){
              MyObject.position=0
              MyObject.mediaPlayer!!.stop()
             val  mediaPlayer= MediaPlayer.create(binding.root.context,Uri.parse(MyObject.list[MyObject.position].musicPath))
              MyObject.mediaPlayer=mediaPlayer
              mediaPlayer!!.start()
          }else{
              MyObject.position=++MyObject.position
              MyObject.mediaPlayer!!.stop()
            val   mediaPlayer= MediaPlayer.create(binding.root.context,Uri.parse(list[MyObject.position].musicPath))
              MyObject.mediaPlayer=mediaPlayer
              mediaPlayer!!.start()
          }



        }

          if (MyObject.mediaPlayer!!.isPlaying){
              binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
          }else{
              binding.pauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
          }


        binding.skipButton.setOnClickListener {
            if (MyObject.position > 0) {
                MyObject.position = --MyObject.position
                MyObject.mediaPlayer?.stop()
                val mediaPlayer =
                    MediaPlayer.create(binding.root.context, Uri.parse(list[MyObject.position].musicPath))
                MyObject.mediaPlayer = mediaPlayer
                mediaPlayer.start()
            }
        }





        binding.seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                 if (fromUser){
                     MyObject.mediaPlayer!!.seekTo(progress)

                 }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })

          runnable= Runnable {
            binding.seekbar.progress=MyObject.mediaPlayer!!.currentPosition
          handler.postDelayed(runnable,1000)
          }




        handler=Handler()
        handler.postDelayed(runnable,1000)






        // Inflate the layout for this fragment
        return binding.root
              }

//    @SuppressLint("MissingSuperCall")
//    override fun onAttach(context: Context) {
//
//
//
//    }






    @SuppressLint("MissingSuperCall")
//    override fun onStop() {
//        if (MyObject.mediaPlayer!!.isPlaying){
//            MyObject.mediaPlayer!!.stop()
//        }
//    }








    override fun clicItem(song: Song, position: Int) {


        if (MyObject.mediaPlayer != null) {
            MyObject.mediaPlayer?.stop()
            mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(song.musicPath), null)
            MyObject.mediaPlayer = mediaPlayer
            mediaPlayer?.start()
        }else{
            mediaPlayer = MediaPlayer.create(requireContext(), Uri.parse(song.musicPath), null)
            MyObject.mediaPlayer = mediaPlayer
            mediaPlayer?.start()


        }

    }


}

//     private fun relealeMP(){
//         if (MyObject.mediaPlayer!=null){
//             try {
//                 MyObject.mediaPlayer?.release()
//                 MyObject.mediaPlayer=null
//             }catch (e:Exception){
//                 e.printStackTrace()
//             }
//         }
//     }



fun millisecondToTimer(milliseconds:Long):String?{
    var finalTimerString = ""
    var secondsString = ""

    val hours = (milliseconds / (1000 * 60 * 60)).toInt()
    val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
    val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
    // Add hours if there
    if (hours > 0) {
        finalTimerString = "$hours:"
    }

    secondsString = if (seconds < 10) {
        "0$seconds"
    } else {
        "" + seconds
    }
    finalTimerString = "$finalTimerString$minutes:$secondsString"

    return finalTimerString
}












//    private fun releaseMP() {
//        if (MyObject.mediaPlayer != null) {
//            try {
//                MyObject.mediaPlayer?.release()
//                MyObject.mediaPlayer = null
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//        fun onDetach() {
//            super.onDetach()
//            releaseMP()
//        }
//    }



