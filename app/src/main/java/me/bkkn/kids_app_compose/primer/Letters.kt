package me.bkkn.kids_app_compose.primer

import android.util.Log

object Letters {

    sealed class Let {
        override fun toString(): String {
            return super.toString()
        }
    }

    sealed class a() : Let() {
        override fun toString(): String {
            return "а"
        }
    }

    sealed class b() : Let() {
        override fun toString(): String {
            return "б"
        }
    }

    sealed class ve() : Let() {
        override fun toString(): String {
            return "в"
        }
    }

    sealed class ge() : Let() {
        override fun toString(): String {
            return "г"
        }
    }

    sealed class de() : Let() {
        override fun toString(): String {
            return "д"
        }
    }

    sealed class ye() : Let() {
        override fun toString(): String {
            return "е"
        }
    }

    sealed class yo() : Let() {
        override fun toString(): String {
            return "ё"
        }
    }

    sealed class je() : Let() {
        override fun toString(): String {
            return "ж"
        }
    }

    sealed class ze() : Let() {
        override fun toString(): String {
            return "з"
        }
    }

    sealed class i() : Let() {
        override fun toString(): String {
            return "и"
        }
    }

    sealed class k() : Let() {
        override fun toString(): String {
            return "к"
        }
    }

    sealed class le() : Let() {
        override fun toString(): String {
            return "л"
        }
    }

    sealed class me() : Let() {
        override fun toString(): String {
            return "м"
        }
    }

    sealed class ne() : Let() {
        override fun toString(): String {
            return "н"
        }
    }

    sealed class o() : Let() {
        override fun toString(): String {
            return "о"
        }
    }

    sealed class pe() : Let() {
        override fun toString(): String {
            return "п"
        }
    }

    sealed class re() : Let() {
        override fun toString(): String {
            return "р"
        }
    }

    sealed class se() : Let() {
        override fun toString(): String {
            return "с"
        }
    }

    sealed class te() : Let() {
        override fun toString(): String {
            return "т"
        }
    }

    sealed class u() : Let() {
        override fun toString(): String {
            return "у"
        }
    }

    sealed class fe() : Let() {
        override fun toString(): String {
            return "ф"
        }
    }

    sealed class he() : Let() {
        override fun toString(): String {
            return "х"
        }
    }

    sealed class ce() : Let() {
        override fun toString(): String {
            return "ц"
        }
    }

    sealed class che() : Let() {
        override fun toString(): String {
            return "ч"
        }
    }

    sealed class she() : Let() {
        override fun toString(): String {
            return "ш"
        }
    }

    sealed class sche() : Let() {
        override fun toString(): String {
            return "щ"
        }
    }

    sealed class eh() : Let() {
        override fun toString(): String {
            return "э"
        }
    }

    sealed class yu() : Let() {
        override fun toString(): String {
            return "ю"
        }
    }

    sealed class ya() : Let() {
        override fun toString(): String {
            return "я"
        }
    }


    fun getAll(): List<Letter> {
        val alLLetters: ArrayList<Letter> = arrayListOf();

        for (char in 'а'..'я') {
            alLLetters.add(Letter(char = char))
            val str = alLLetters.last()
            Log.i("tag", str.char.toString())
        }


        return alLLetters
    }


    val vowels: List<Letter> = listOf(
        Letter('а'),
        Letter('и'),
        Letter('у'),
        Letter('э'),
        Letter('о'),

        Letter('я'),
        Letter('ы'),
        Letter('ю'),
        Letter('е'),
        Letter('ё'),
    )

    val consonants = listOf(
        Letter('б'),
        Letter('в'),
        Letter('г'),
        Letter('д'),
        Letter('ж'),
        Letter('з'),
        Letter('й'),
        Letter('к'),
        Letter('л'),
        Letter('м'),
        Letter('н'),
        Letter('п'),
        Letter('р'),
        Letter('с'),
        Letter('т'),
        Letter('ф'),
        Letter('х'),
        Letter('ц'),
        Letter('ч'),
        Letter('ш'),
        Letter('щ')
    )

    fun silableListOfCons(consonant: Letter): MutableList<Syllable> {
        var list = mutableListOf<Syllable>()
        for (v in vowels) {
            list.add(consonant + v)
        }
        return list
    }

    fun silableListOfVowel(vowel: Letter): MutableList<Pronounceable> {
        var list = mutableListOf<Pronounceable>()
        for (c in consonants) {
            list.add(c + vowel)
        }
        return list
    }

    fun consonants_str(): Collection<String> {
        var list = mutableListOf<String>()
        for (c in consonants) {
            list.add(c.char.toString())
        }
        return list
    }

    fun vowels_str(): Collection<String> {
        var list = mutableListOf<String>()
        for (v in vowels) {
            list.add(v.char.toString())
        }
        return list
    }


}

private operator fun Letter.plus(letter: Letter): Syllable {
    return Syllable(this.char, letter.char)
}
