package me.bkkn.kids_app_compose

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*
import me.bkkn.kids_app_compose.model.MainViewModel
import me.bkkn.kids_app_compose.primer.Pronounceable
import me.bkkn.kids_app_compose.primer.Syllable
import me.bkkn.kids_app_compose.primer.Word
import java.io.File
import java.net.URL
import java.nio.charset.Charset

//import com.github.demidko.aot.WordformMeaning.lookupForMeanings

val MYTAG : String = "@@@"
val elev = 10.dp
val fntSize = 32.sp
var wordList = mutableStateListOf<String>()
var wordDictLesson1 =
    mutableListOf<String>("папа", "мама", "наша", "Ната", "Маша", "Паша", "Наташа", "панама")
var wordDictLesson2 =
    mutableListOf<String>("папа", "мама", "наша", "Ната", "Маша", "Паша", "Наташа", "панама","рада","тара","дама","рама","рана")
var namesDictLesson = mutableListOf<String>(
    "Даша",
    "Капа",
    "Лада",
    "Лара",
    "Маша",
    "Ната",
    "Наташа",
    "Паша",
    "Тамара"
)
var buffer = ""
var sequenceList = ArrayDeque<String>();// mutableListOf<String>();
fun wordCheck(context: Context, word: String): Boolean {

    return 0 != context.resources.getIdentifier(
        "sound${Word(word).soundId()}",
        "raw",
        context.packageName
    )
}

@ExperimentalMaterialApi
@Composable
fun Lesson1(context: Context, viewModel: MainViewModel) {

    var word: MutableState<String> = remember { mutableStateOf<String>("----") }

    Column(
        Modifier
            .fillMaxWidth()
            .height(620.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()

                .padding(1.dp),
            Arrangement.SpaceEvenly,
            Alignment.Top
        ) {
            RowItem(word, context, viewModel, Syllable('п', 'а'))
            RowItem(word, context, viewModel, Syllable('м', 'а'))
            RowItem(word, context, viewModel, Syllable('ш', 'а'))
            RowItem(word, context, viewModel, Syllable('н', 'а'))
            RowItem(word, context, viewModel, Syllable('т', 'а'))
        }

//        Row() {
//            WordItem(context = context, Word(word.value))
//        }

        Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {

//            for (it in wordList) {
//                if (wordCheck(context, it))
//                    WordItem(context, viewModel, Word(it))
//                else
//                    wordList.remove(it)
//            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Lesson2(context: Context, viewModel: MainViewModel) {

    var word: MutableState<String> = remember { mutableStateOf<String>("----") }

    Column(
        Modifier
            .fillMaxWidth()
            .height(620.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()

                .padding(1.dp),
            Arrangement.SpaceEvenly,
            Alignment.Top
        ) {
            RowItem(word, context, viewModel, Syllable('п', 'а'))
            RowItem(word, context, viewModel, Syllable('м', 'а'))
            RowItem(word, context, viewModel, Syllable('ш', 'а'))
            RowItem(word, context, viewModel, Syllable('н', 'а'))
            RowItem(word, context, viewModel, Syllable('т', 'а'))
            RowItem(word, context, viewModel, Syllable('д', 'а'))
            RowItem(word, context, viewModel, Syllable('р', 'а'))
        }

//        Row() {
//            WordItem(context = context, Word(word.value))
//        }

        Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {

            for (it in wordList) {
                //if (wordCheck(context, it))
                    WordItem(context, viewModel, Word(it))
//                else
//                    wordList.remove(it)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun WordItem(context: Context, viewModel: MainViewModel, text: Pronounceable) {
    Card(
        backgroundColor = Color.Magenta,
        elevation = elev,
        shape = RoundedCornerShape(10),
        onClick = {
//            text.pronounce(context = context)
            viewModel.speak(text.text());
        }
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = text.text(),
                fontSize = fntSize,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun RowItem(
    word: MutableState<String>,
    context: Context,
    viewModel: MainViewModel,
    text: Pronounceable
) {
    val scope = rememberCoroutineScope()
    Card(
        elevation = elev,
        shape = RoundedCornerShape(10),
        onClick = {
            sequenceList.addLast(text.text())
            if(sequenceList.size > 10)
                sequenceList.removeFirst()
            addWord(scope);
            viewModel.speak(text.text());
        }
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = text.text(),
                fontSize = fntSize,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun addWord(scope: CoroutineScope) {
    findAllWords(scope)
}

private suspend fun httpGet(myURL: String?): String? {

    val result = withContext(Dispatchers.IO) {
        URL(myURL).readText(Charset.forName("Windows-1251"))
    }
    return result
}

fun readFileLineByLineUsingForEachLine(fileName: String)
        = File(fileName).forEachLine { Log.d(MYTAG, it) }

fun findAllWords(scope: CoroutineScope) {

//    viewModel()
//    getResources().getIdentifier("FILENAME_WITHOUT_EXTENSION",
//        "raw", getPackageName());

    readFileLineByLineUsingForEachLine("/Users/bkkn/AndroidStudioProjects/KidsAppCompose/app/src/main/res/raw/russian_dict.txt")

    var allWords: String
//    GlobalScope.launch(Dispatchers.Main) {
//        val result =
//            httpGet("https://raw.githubusercontent.com/danakt/russian-words/master/russian.txt")
//        allWords = result.toString()
//    }

//    val res = File("C:\\Users\\HOME-PC\\AndroidStudioProjects\\MyComoseLists\\app\\src\\main\\res\\raw\\russian.txt").readText(Charset.forName("Windows-1251"))
    if (sequenceList.size < 2)
        return
    for (i in 1..sequenceList.size - 1) {
        val wordCandidate = sequenceList[i - 1] + sequenceList[i]
        var found = wordDictLesson2.find { it.lowercase() == wordCandidate.lowercase() }
        var foundInNames = namesDictLesson.find { it.lowercase() == wordCandidate.lowercase() }
        if (found != null) {
            if (!found.isEmpty()){
                if(wordList.find { it.lowercase() == wordCandidate.lowercase() } == null)
                    wordList.add(found)
            }
        }
        if (foundInNames != null) {
            if (!foundInNames.isEmpty())
                if(wordList.find { it.lowercase() == wordCandidate.lowercase() } == null)
                    wordList.add(foundInNames)
        }
    }
    if (sequenceList.size < 3)
        return
    for (i in 2..sequenceList.size - 1) {
        val wordCandidate = sequenceList[i - 2] + sequenceList[i-1] + sequenceList[i]
        var found = wordDictLesson2.find { it.lowercase() == wordCandidate.lowercase() }
        var foundInNames = namesDictLesson.find { it.lowercase() == wordCandidate.lowercase() }
        if (found != null) {
            if (!found.isEmpty()){
                if(wordList.find { it.lowercase() == wordCandidate.lowercase() } == null)
                    wordList.add(found)
            }
        }
        if (foundInNames != null) {
            if (!foundInNames.isEmpty())
                if(wordList.find { it.lowercase() == wordCandidate.lowercase() } == null)
                    wordList.add(foundInNames)
        }
    }

}

