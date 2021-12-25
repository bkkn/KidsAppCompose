package me.bkkn.kids_app_compose.utils

import me.bkkn.kids_app_compose.primer.Syllable

class Helper {

    fun fileNameBySyllable(str: String): String {
        val name = Syllable(str.get(0), str.get(1)).soundId();
        return name
    }
}