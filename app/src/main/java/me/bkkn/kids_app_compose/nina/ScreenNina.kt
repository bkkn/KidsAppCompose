package me.bkkn.kids_app_compose.nina

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import me.bkkn.kids_app_compose.model.MainViewModel

//@Composable
//fun ScreenNina(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Green)
//    )
//}

@Composable
fun ScreenNina(navController: NavController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        val nina = viewModel.personList.get(7)
        GlideImage(
            imageModel = nina.imgUrl,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(nina.imgId),
            // shows an error ImageBitmap when the request failed.
            error = ImageBitmap.imageResource(nina.imgId),
            modifier = Modifier.fillMaxSize()
        )

    }
}