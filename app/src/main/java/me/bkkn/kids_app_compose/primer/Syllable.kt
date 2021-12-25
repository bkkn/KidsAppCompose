package me.bkkn.kids_app_compose.primer

class Syllable(val first: Char, val second: Char) : Pronounceable {
    override fun soundId(): String {
        return first.code.toString() + second.code.toString()
    }

    override fun text(): String {
        return first.toString() + second.toString()
    }

}
