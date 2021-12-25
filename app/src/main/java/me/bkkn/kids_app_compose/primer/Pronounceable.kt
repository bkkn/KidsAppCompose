package me.bkkn.kids_app_compose.primer

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast

interface Pronounceable {
    fun pronounce(context: Context) {
        val res = context.resources.getIdentifier("sound${soundId()}", "raw", context.packageName)
        if (res == 0)
            Toast.makeText(context, "resource not found" + " sound${soundId()}", Toast.LENGTH_SHORT).show()
        else {
            Toast.makeText(context, "${soundId()}", Toast.LENGTH_SHORT).show()
            MediaPlayer.create(context, res).start()
        }
    }

    abstract fun soundId(): String

    fun text(): String
}
