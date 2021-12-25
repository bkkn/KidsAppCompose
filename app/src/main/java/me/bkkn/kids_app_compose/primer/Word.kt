package me.bkkn.kids_app_compose.primer

class Word(val text: String) : Pronounceable {
    override fun soundId(): String {
        var res = ""
        for (char in text){
            res += char.code.toString()
        }
        return res
    }

    override fun text(): String {
        return text
    }

}

