package me.bkkn.kids_app_compose.persons

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import me.bkkn.kids_app_compose.model.MainViewModel
import me.bkkn.kids_app_compose.model.MainViewModel.Default.PERSON_INT
import me.bkkn.kids_app_compose.model.MainViewModel.Default.PERSON_NAME
import me.bkkn.kids_app_compose.model.MainViewModel.Default.PERSON_SOUND
import me.bkkn.kids_app_compose.model.MainViewModel.Default.PERSON_URL
import java.io.File

data class Person(val name: String = PERSON_NAME, val imgId: Int = PERSON_INT, val imgUrl: String = PERSON_URL, val soundId: Int = PERSON_SOUND) {
    fun playSong(viewModel : MainViewModel, context: Context) {
        viewModel.mediaPlayer.apply {
            if(isPlaying)
                return
            reset();
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            val uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + File.pathSeparator + File.separator + File.separator
                        + context.getPackageName()
                        + File.separator
                        + soundId
            );
            setDataSource(context, uri)
            prepare()
            start()
        }
    }
}