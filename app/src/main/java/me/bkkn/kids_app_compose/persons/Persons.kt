package me.bkkn.kids_app_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import me.bkkn.kids_app_compose.model.MainViewModel
import me.bkkn.kids_app_compose.persons.Person
import kotlin.random.Random

@ExperimentalFoundationApi
@Composable
fun PersonsScreen(navController: NavController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val optionsState = viewModel.currentOptionState.observeAsState()
//    val personIdState = viewModel.currentPersonState
//    val mediaPlayer: MediaPlayer
    LazyVerticalGrid(
        cells = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(viewModel.personList) { item ->
            Card(
                modifier = Modifier.padding(4.dp),
                backgroundColor = Color(
                    red = Random.nextInt(0, 255),
                    green = Random.nextInt(0, 255),
                    blue = Random.nextInt(0, 255)
                )
            ) {
//                mediaPlayer. create(context, item.imgId)
                val scope = rememberCoroutineScope()

                GlideImage(
                    imageModel = item.imgUrl,
                    // Crop, Fit, Inside, FillHeight, FillWidth, None
                    contentScale = optionsState.value!!,
                    // shows an image with a circular revealed animation.
                    circularReveal = CircularReveal(duration = 250),
                    // shows a placeholder ImageBitmap when loading.
                    placeHolder = ImageBitmap.imageResource(item.imgId),
                    // shows an error ImageBitmap when the request failed.
                    error = ImageBitmap.imageResource(item.imgId),
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                viewModel.currentPersonState.value = item
                                navController.navigate(NavigationItem.Content.route)
                                scope.launch {
                                    item.playSong(viewModel, context)
                                }
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun PersonItem(navController: NavController, id: Int) {

//    Image(
//        painter = rememberImagePainter("https://www.gstatic.com/webp/gallery/1.sm.jpg"),
//        modifier = Modifier.fillMaxSize(),
//        contentDescription = "Image Description"
//    )

    GlideImage(
        imageModel = "https://www.gstatic.com/webp/gallery/1.sm.jpg",
        // Crop, Fit, Inside, FillHeight, FillWidth, None
        contentScale = ContentScale.Crop,
        // shows an image with a circular revealed animation.
        circularReveal = CircularReveal(duration = 250),
        // shows a placeholder ImageBitmap when loading.
        placeHolder = ImageBitmap.imageResource(R.drawable.egor),
        // shows an error ImageBitmap when the request failed.
        error = ImageBitmap.imageResource(R.drawable.gif)
    )

//    Image(
//        painterResource(id),
//        contentDescription = "",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier
//            .clickable(
//                onClick = {
//                    viewModel.currentPersonState.value = id
//                    navController.navigate(NavigationItem.Content.route)
//                }
//            )
//    )
}

@Composable
fun ContentScreen(navController: NavController, viewModel: MainViewModel) {

    val person = viewModel.currentPersonState.value ?: Person()

    val context = LocalContext.current
    Column() {
        Row() {
            GlideImage(
                imageModel = person.imgUrl,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                // shows an image with a circular revealed animation.
                circularReveal = CircularReveal(duration = 250),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(person.imgId),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(person.imgId),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            navController.navigate(NavigationItem.Persons.route)
                        }
                    )
            )

//            Image(
//                painterResource(person.imgId),
//                contentDescription = "",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable(
//                        onClick = {
//                            navController.navigate(NavigationItem.Persons.route)
//                        }
//                    )
//            )
//            GlideImage(
//                imageModel = "https://sun9-32.userapi.com/c9984/u911309/-6/x_c23a06a5.jpg",
//                // Crop, Fit, Inside, FillHeight, FillWidth, None
//                contentScale = ContentScale.Crop,
//                // shows an image with a circular revealed animation.
//                circularReveal = CircularReveal(duration = 250),
//                // shows a placeholder ImageBitmap when loading.
//                placeHolder = ImageBitmap.imageResource(R.drawable.egor),
//                // shows an error ImageBitmap when the request failed.
//                error = ImageBitmap.imageResource(R.drawable.egor_gray)
//            )
        }
    }
}