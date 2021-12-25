package me.bkkn.kids_app_compose.masha

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavController
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import me.bkkn.kids_app_compose.Lesson2
import me.bkkn.kids_app_compose.model.MainViewModel

@ExperimentalMaterialApi
@Composable
fun ScreenMasha(context: Context, navController: NavController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
//        MainColumn(context = context)
        val masha = viewModel.personList.get(8)
        GlideImage(
            imageModel = masha.imgUrl,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            // shows an image with a circular revealed animation.
            circularReveal = CircularReveal(duration = 250),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(masha.imgId),
            // shows an error ImageBitmap when the request failed.
            error = ImageBitmap.imageResource(masha.imgId),
            modifier = Modifier.fillMaxSize()
        )

//        Lesson1(context)
        Lesson2(context,viewModel)
    }
}