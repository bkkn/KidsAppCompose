package me.bkkn.kids_app_compose.model

import android.content.Intent
import android.content.res.TypedArray
import android.media.MediaPlayer
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.bkkn.kids_app_compose.R
import me.bkkn.kids_app_compose.persons.Person

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel(), LifecycleObserver {

    private lateinit var textToSpeechEngine: TextToSpeech
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    fun initial(
        engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        textToSpeechEngine = engine
        startForResult = launcher
    }

    fun displaySpeechRecognizer() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("ru_RU"))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("ru-RU"))
        })
    }

    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    companion object Default {
        const val PERSON_NAME: String = ""
        const val PERSON_URL: String = ""
        const val PERSON_INT = 0
        const val PERSON_SOUND = 0
    }

    var currentPersonState = MutableLiveData(Person())
    var currentOptionState = MutableLiveData(ContentScale.Crop)
    val mediaPlayer: MediaPlayer = MediaPlayer()
    var personList: ArrayList<Person> = arrayListOf()
    fun initPersonList(arr: TypedArray) {
        personList = arrayListOf(
            Person(
                "egor",
                arr.getResourceId(0, -1),
                "https://sun9-32.userapi.com/c9984/u911309/-6/x_c23a06a5.jpg",
                R.raw.summertimesadness
            ),
            Person(
                "eugen",
                arr.getResourceId(1, -1),
                "https://scontent-arn2-1.cdninstagram.com/v/t51.2885-15/e15/s480x480/10598748_523442151123498_885643893_n.jpg?_nc_ht=scontent-arn2-1.cdninstagram.com&_nc_cat=106&_nc_ohc=ZnTr5h3SwRYAX_Ah5oz&edm=ABfd0MgBAAAA&ccb=7-4&oh=9a32cc84e3c261c01b2bbef19c89ef76&oe=61952D95&_nc_sid=7bff83",
//                "https://sun9-88.userapi.com/c10310/u99821872/a_3f46b9ca.jpg",
                R.raw.california
            ),
            Person("lisa", arr.getResourceId(2, -1), "", R.raw.bremmuz),
            Person(
                "marina",
                arr.getResourceId(3, -1),
                "https://scontent-arn2-1.cdninstagram.com/v/t51.2885-15/e35/s480x480/117647898_2854721028083209_8965768706914643351_n.jpg?_nc_ht=scontent-arn2-1.cdninstagram.com&_nc_cat=101&_nc_ohc=adYucrjWqwIAX_-FPqX&edm=ABfd0MgBAAAA&ccb=7-4&oh=b2de628b1e05a35dcf941ad470364fcd&oe=6195FC03&_nc_sid=7bff83",
                R.raw.mamamaria
            ),
            Person("me", arr.getResourceId(4, -1), "", R.raw.geraman),
            Person(
                "nastya",
                arr.getResourceId(5, -1),
                "https://scontent-arn2-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/s750x750/208986843_376665073793471_3031508252231875016_n.jpg?_nc_ht=scontent-arn2-1.cdninstagram.com&_nc_cat=111&_nc_ohc=FeFX1aPqJgQAX9vttPN&edm=AABBvjUBAAAA&ccb=7-4&oh=9c1045e0d5cbbafe254dc25f1c9a9ba8&oe=61961B7E&_nc_sid=83d603",
                R.raw.nino
            ),
            Person("nikola", arr.getResourceId(6, -1), "", R.raw.ifollow),
            Person("nina", arr.getResourceId(7, -1), "", R.raw.aladin_smeloy),
            Person(
                "masha",
                arr.getResourceId(8, -1),
//                "https://lh3.googleusercontent.com/xzcWV43jEDTu8oayq8q_FJ4m4ocmbNZ8V7JJ8EatnmNRPr2O1T8QLP8gYO6alyLdOJHXL1yfRLMwdwslRh_NIKlpiIGhcXBuTQNXIYq8bz1SDgjrAUuQWFQi_tbV1QYkiL7oQklVAIUtQpt3wpp85ZJtTIvBgUmmNnOocRip6DcRp2ka-X5mjkEIhAuQzH-nJycrcBkZJbLNWaVIGGlb4lShfqUQZvcmt42HEcQdib0rVFrTSo4zowoffN6tv15v6V4fo4svw8uI99cPkh6_bQB-aYYZltZKaTGNt511I_WDGSF1CXdjhkNosJvMP9UEhORbgS0WimYz0y6JUO7wGr4T56uHWUDtiW-dxgGy1CePs7gXgrAxhQElL9xDEajdWleUQjUZYAZjHbGpuO7zDkPMn4e9CaUAYiM-cAzAfpp6gS4BMJYs8VycKUBOV_IbZBPY2sd-NSKi4Zg2ObWZtZNwqS0tRTiyhULzvH43LtbdHUz9WHqobPAfz4wg7m37p12AP7SaT2eWfQXK0jJVxCtDocOPGESGqElSJyXAvbGlrh9Ofi7kt3DdGIzUXnErENHwKbe4QghfF-YlCAA_RZFDPhv7YRcvjhqCFz6w6jPuY-c7g6ERfxE_87wAdFZu30H2LN1k9m3iy0ydamxw_epU8m3FszL_Xe7z1a8hlZ6AkDs_S3uWpE9l9E8qxhOSI95oQPuHrtrTEKEfb0gLdjMmlg=w2813-h1875-no?authuser=0",
                "https://lh3.googleusercontent.com/2Us2bTU7Q1_YvXW6V7dXe_wZVrvCDt0IDZdvq3Fm3QJ_5vd9PEA9xgk7FQWzvgtoH_CheGAKQ_5vRLVMokxQA-PunK3fdkneN44iRtp3owWu1o3g4YIMdAfujx885prBiwQAn_uHYB5KcAuHixdl1SntNf9tQG5oeBcilhaxhkGn_o2Waso1mElxYwU1UNbui9swrTITtI2voTKV7-dJhtLWx3gtPnzY0wg1qq7B7pRve6xWWpv4I636huGypAN4b4mMhb3BpyJrfrKkqi9-14AT6bVEhD-HFW5ikTLCtD1wxWngYD2v40rgQb-LMqAc3hgLqdXp5AOAAFhv3dLiXtDu7F8xgtM4LJcJWSkVz2k1N3kNrtP-abpvaHhOsdrd0wn95CheBzWyVsyuoObqmqDdMkgh3xaqoVHpnrZkB0ZdS7-FHp1QiF6wDOpt9zvo6_FnUJjpQrTJZZeWyMTbXcaR5iEOPwZynSocyYqJJl3evMM1enuhqNN-8J_7TJ2mfYtOadA3nCSQc2yrRyOedTAnKWiG9ex_1zDs3vneU3vaoqXWW3vAAT_szet38VPkLF6vE1fsydIX4bdjmj-nTLCrdW32ov_ayZmYK6AMW7_Up8tPVNzZwhsTv6n5n8T7phdRnNjE8cZnxejdScnKWc4OUAbmZ6xQBClJeMcxONCN5iBNC9CPJMr-bk0yJZybMVkAauxT2Gi9rSGvc8CdT5bYbQ=w845-h1875-no?authuser=0",
                R.raw.otpusti_i_zabud
            ),
            Person(
                "sasha",
                arr.getResourceId(9, -1),
                "https://lh3.googleusercontent.com/ecX4PFeIM3EDLVtaKOsJd0A8ux1_OrRU5G_N0RKP6i2eL5MjhvXTg9tmgzExFeeJbm0rpHiRHrh3Crq_4fRIdKEx2dnubAld0ukYq-ya41ZZKaKZBXmFySdBPvO6yz1FsNQEpjHKVXrp_00hA8ALfvtTBhsll8Ax4csbXHFbZ8W-nFkxPg1Cl7JpF97s9B7_mV69rGBgeeHiOtSPuvbGg5Vrj_sunwtajcStKufFbDuJ8V1qsJ7W4w4fuPjhf7JTod_3DCOoQXPu4JdVo_68-tAGv3SmIPOM_VFXbtNxPaTfnm1BBG5JPv7K6UTiJOrO8nm7eKmDMj0fcMLn8wN1IO9ph0TDbA_P2722er0kYOe-Qm9dLt9pqAgn7OTQSplFQPgiEELS5snVlASdjCpWIaYeRAIrM4yUCQcUJ9qySkcBR3G3viqWLjyq1S4HA0FAKR4yJvZqCwm8jdoKIWZci4YHvOW-bxHZH7trknfxnGhsS5_NvZekswXtuwLDyy2JheAUU6E5kNiPmVTYd6kYmT2uT9-ta4BSGYnCQmV7vXUt7hD5X1mCJpsKI-iD4hpEH9mQvDlLmIhgaz15RtALKY1kDNyq5nxgect8zwKsyqeShjxluHfjhrriVh5cu6r6PPXF1eMT2JVO7ofEsjPe6d4HYdi7uhww1dtuE5zlTpQwUMxSDgFQuXfHK1V_kUQ7kwxCLcDoQuAjDiUxb81ODJTdeg=w297-h167-no?authuser=0",
                R.raw.za_oknom_opyat_sugroby
            ),
            Person("sereja", arr.getResourceId(10, -1), "", R.raw.crazy),
            Person("sonya2", arr.getResourceId(11, -1), "", R.raw.bremmuz),
            Person("tanya", arr.getResourceId(12, -1), "", R.raw.naregon),
            Person("vanes", arr.getResourceId(13, -1), "", R.raw.oysya),
            Person("verona", arr.getResourceId(14, -1), "", R.raw.chtotebe),
            Person("vitek", arr.getResourceId(15, -1), "", R.raw.bombom),
            Person(
                "volodja",
                arr.getResourceId(16, -1),
                "https://thumb.tildacdn.com/tild3534-3063-4333-a261-393138656465/-/format/webp/_-2-2.jpg",
                R.raw.enniomorricone1
            ),
            Person("ira", arr.getResourceId(17, -1), "", R.raw.elton_john),
            Person("vanya_macnev", arr.getResourceId(18, -1), "", R.raw.george_michael),
            Person("nikita", arr.getResourceId(19, -1), "", R.raw.bombom),
            Person("vika", arr.getResourceId(20, -1), "", R.raw.bombom),
            Person("mama", arr.getResourceId(21, -1), "", R.raw.bombom),
            Person("papa", arr.getResourceId(22, -1), "", R.raw.bombom),
            Person("egor_bakaikin", arr.getResourceId(23, -1), "", R.raw.bombom)
//            Person("danya", arr.getResourceId(24, -1), "", R.raw.bombom)
        )
    }

//    fun initTts(context: Context) {
//        tts = TextToSpeech(context, this)
//    }

//    var tts: TextToSpeech? = null
//
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            // set US English as language for tts
//
//////            val result = tts!!.setLanguage(Locale("RU").language)
////            val lang = Locale("US").language
////            val local = Locale("ru")
//            val result = tts!!.setLanguage(Locale("ru"))
//
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "The Language specified is not supported!")
//            } else {
////                buttonSpeak!!.isEnabled = true
//            }
//
//        } else {
//            Log.e("TTS", "Initilization Failed!")
//        }
//
//        speakOut()
//    }
//
//
//    fun speakOut() {
//        val text = "Стоп Стоп Стоп Стоп"
//        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
//    }

//    public override fun onDestroy() {
//        // Shutdown TTS
//        if (tts != null) {
//            tts!!.stop()
//            tts!!.shutdown()
//        }
//        super.onDestroy()
//    }
//    fun onPersonChange(newPerson: Int) {
//        if (mediaPlayer.isPlaying)
//            mediaPlayer.stop()
//        currentPersonState.value = newPerson
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    protected fun onLifeCycleStop() {
//        if(mediaPlayer.isPlaying)
//            mediaPlayer.stop()
//    }


}
