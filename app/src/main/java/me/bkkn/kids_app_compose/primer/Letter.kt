package me.bkkn.kids_app_compose.primer

class Letter(val char: Char) : Pronounceable {
    override fun soundId(): String {
        return char.code.toString()
    }

    override fun text(): String {
        return char.toString()
    }

}