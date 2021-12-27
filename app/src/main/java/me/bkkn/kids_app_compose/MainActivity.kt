package me.bkkn.kids_app_compose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import me.bkkn.kids_app_compose.kolya.ScreenKolya
import me.bkkn.kids_app_compose.masha.ScreenMasha
import me.bkkn.kids_app_compose.model.MainViewModel
import me.bkkn.kids_app_compose.nina.ScreenNina
import me.bkkn.kids_app_compose.primer.Letters
import me.bkkn.kids_app_compose.primer.Pronounceable
import me.bkkn.kids_app_compose.ui.theme.KidsAppComposeTheme
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.app.ActivityCompat

import android.os.Build
import java.nio.charset.Charset


var list = mutableStateListOf<Pronounceable>()
var fontSize = mutableStateOf(64)

@Composable
fun Recognize() {

    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }

    Button(onClick = { launcher.launch() }) {
        Text(text = "Take a picture")
    }

    result.value?.let { image ->
        Image(image.asImageBitmap(), null, modifier = Modifier.fillMaxWidth())
    }

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Persons,
        NavigationItem.Content,
        NavigationItem.Kolya,
        NavigationItem.Nina,
        NavigationItem.Masha
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, item.route) },
                label = { Text(text = item.route) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                    if (item.route == NavigationItem.Kolya.route) {
                        navController.navigate(item.route)
                    }
                    if (item.route == NavigationItem.Nina.route) {
                        navController.navigate(item.route)
                    }
                    if (item.route == NavigationItem.Masha.route) {
                        navController.navigate(item.route)
                    }
                    if (item.route == NavigationItem.Persons.route) {
                        navController.navigate(item.route)
                    }
                    if (item.route == NavigationItem.Content.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

//val viewModel: MainViewModel = MainViewModel()
//lateinit var viewModel : MainViewModel
var personIds: ArrayList<Int> = arrayListOf()

val TAG = "@@@"

class MainActivity : ComponentActivity() {

    private val model: MainViewModel by viewModels()

//    fun isStoragePermissionGranted(): Boolean {
//        return if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED
//            ) {
//                Log.v(TAG, "Permission is granted")
//                true
//            } else {
//                Log.v(TAG, "Permission is revoked")
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    1
//                )
//                false
//            }
//        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG, "Permission is granted")
//            true
//        }
//    }
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//    val inputStream = application.resources.openRawResource(R.raw.russian_dict)
//    val text = inputStream.readBytes().toString(Charset.defaultCharset())
//    Log.d(TAG, text)

//        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Log.v(TAG, "Permission is granted");
//            //File write logic here
//            val inputStream = application.resources.openRawResource(R.raw.russian_dict)
//            model.setInputStream(inputStream)
//        } else {
//            if(isStoragePermissionGranted()){
//                val inputStream = application.resources.openRawResource(R.raw.russian_dict)
//                model.setInputStream(inputStream)
//            }
//        }

//        val resId = resources.getIdentifier("russian_dict.txt","raw", getPackageName())

        model.initial(textToSpeechEngine, startForResult)
        val arr = resources.obtainTypedArray(R.array.persons_drawable_list)
        (0 until arr.length()).forEach {
            personIds.add(arr.getResourceId(it, -1))
        }
        setContent {
            KidsAppComposeTheme {
                MainScreen(applicationContext, arr)
            }
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
//            val spokenText: String? =
//                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//                    .let { text -> text?.get(0) }
//            binding.edtText.setText(spokenText)
        }
    }

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
        }
    }
//
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            // set US English as language for tts
//            val result = tts!!.setLanguage(Locale.US)
//
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS","The Language specified is not supported!")
//            } else {
////                buttonSpeak!!.isEnabled = true
//            }
//
//        } else {
//            Log.e("TTS", "Initilization Failed!")
//        }
//
//    }
//
//    public fun speakOut() {
//        val text = "STOP"
//        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
//    }
//
//    public override fun onDestroy() {
//        // Shutdown TTS
//        if (tts != null) {
//            tts!!.stop()
//            tts!!.shutdown()
//        }
//        super.onDestroy()
//    }

}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(context: Context, arr: TypedArray, viewModel: MainViewModel = viewModel()) {

    viewModel.initPersonList(arr)

    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        drawerContent = {


            Box(modifier = Modifier.fillMaxSize()) {
                GlideImage(
                    imageModel = " kolya.imgUrl",
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = ContentScale.Crop,
                    // shows an image with a circular revealed animation.
                    circularReveal = CircularReveal(duration = 250),
                    // shows a placeholder ImageBitmap when loading.
                    placeHolder = ImageBitmap.imageResource(R.drawable.egor_gray),
                    // shows an error ImageBitmap when the request failed.
                    error = ImageBitmap.imageResource(R.drawable.egor_gray),
                    modifier = Modifier.fillMaxSize()
                )

                Surface(Modifier.background(Color.Green)) {

                    Column(modifier = Modifier.background(Color.Green)) {

                        Button(modifier = Modifier.fillMaxWidth(),
                            onClick =
                            {
                                if (viewModel.mediaPlayer.isPlaying)
                                    viewModel.mediaPlayer.stop()
                            }) {
                            Text(text = "STOP")
                        }

                        RadioBtns()
                    }
                }
            }
        }
    ) {
        Navigation(context, navController, viewModel)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(context: Context, navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = NavigationItem.Kolya.route) {
        composable(NavigationItem.Content.route) {
            ContentScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavigationItem.Persons.route) {

            //HeaderGrid()

            PersonsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavigationItem.Kolya.route) {
            ScreenKolya(
                navController = navController,
                viewModel
            )
        }
        composable(NavigationItem.Nina.route) {
            ScreenNina(
                navController = navController,
                viewModel
            )
        }
        composable(NavigationItem.Masha.route) { ScreenMasha(context, navController, viewModel) }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White
    )
}

@Composable
fun ScreenDefault(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}

@Composable
fun MainColumn(context: Context) {
    Column {
        UpperRow()
        MiddleRow(context)
        LowerRow()
    }
}

@Composable
fun UpperRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        Arrangement.Center, Alignment.CenterVertically
    ) {
        SizeButtonsRow()
    }
}

@Composable
fun MiddleRow(context: Context) {
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f),
        Arrangement.SpaceBetween, Alignment.CenterVertically
    ) {
        VerticalConsonantsList()
        MainLettersList(context = context, itemList = list)
        VerticalVowelsList()
    }
}

@Composable
fun LowerRow() {
    Column() {

        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            Arrangement.Center, Alignment.CenterVertically
        ) {
            Button(onClick = { list.clear();list.addAll(Letters.consonants) }) {
                Text(text = "согласные")
            }
            Button(onClick = { list.clear();list.addAll(Letters.vowels) }) {
                Text(text = "гласные")
            }
        }
    }
}

@Composable
fun VerticalConsonantsList() {
    LazyColumn(Modifier.width(fontSize.value.dp)) {
        items(Letters.consonants) { consonant ->
            Button(
                onClick = { list.clear();list.addAll(Letters.silableListOfCons(consonant)) },
                modifier = Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Blue)
            ) {
                Text(text = consonant.char.toString())
            }
        }
    }
}

@Composable
fun MainLettersList(context: Context, itemList: List<Pronounceable>) {
    LazyColumn() {
        items(itemList) { item ->
            MainListItemRow(context, item)
        }
    }
}

@Composable
fun VerticalVowelsList() {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(Letters.vowels) { vowel ->
            Button(
                onClick = { list.clear();list.addAll(Letters.silableListOfVowel(vowel)) },
                modifier = Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Blue)
            ) {
                Text(text = vowel.char.toString())
            }
        }
    }
}

@Composable
fun ItemsList(context: Context, itemList: List<Pronounceable>) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(), Arrangement.SpaceEvenly
    ) {

        Button(onClick = { list.clear();list.addAll(Letters.consonants) }) {
            Text(text = "согласные")
        }



        Button(onClick = { list.clear();list.addAll(Letters.vowels) }) {
            Text(text = "гласные")
        }



        LazyColumn() {

            items(itemList) { item ->
                MainListItemRow(context, item)
            }
        }


    }
}

@Composable
fun SizeButtonsRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .width(100.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.White, Color.Red, Color.Cyan
                    )
                )
            ), Arrangement.SpaceBetween
    ) {
        RoundButton(
            onClick = { fontSize.value = (10 + fontSize.value) },
            imageVector = Icons.Default.KeyboardArrowUp,
            "+"

        )
        RoundButton(
            onClick = { fontSize.value = (-10 + fontSize.value) },
            imageVector = Icons.Default.KeyboardArrowDown,
            "-"
        )
    }
}

@Composable
fun RoundButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(100.dp),  //avoid the oval shape
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.Blue),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),

        ) {
//        Icon(imageVector, contentDescription = "content description")
        Text(text = text, Modifier, fontSize = 50.sp)
    }
}

@Composable
fun LetterButton(onClick: () -> Unit, text: String) {
    Text(text = text)
}

@Composable
fun MainListItemRow(context: Context, item: Pronounceable) {
    Surface(Modifier.background(color = Color.Magenta)) {

        Text(
            modifier = Modifier
                // .fillMaxWidth()
                .clickable {
                    Log.i("tag", "click")
                    item.pronounce(context)
                },
            textAlign = TextAlign.Center,
            text = item.text(),
            fontSize = fontSize.value.sp
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KidsAppComposeTheme {
        //MainColumn(context = context)//MessageList(context = context, messages = list)
    }
}