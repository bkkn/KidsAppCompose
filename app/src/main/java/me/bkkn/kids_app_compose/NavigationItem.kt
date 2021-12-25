package me.bkkn.kids_app_compose

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem (val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Content : NavigationItem ("Content", R.string.content_route, Icons.Filled.Star)
    object Persons : NavigationItem ("Persons", R.string.persons_route, Icons.Filled.Star)
    object Kolya : NavigationItem ("Коля", R.string.kolya_route, Icons.Filled.Star)
    object Nina : NavigationItem("Нина", R.string.nina_route, Icons.Filled.Star)
    object Masha : NavigationItem("Маша", R.string.masha_route, Icons.Filled.Star)
}
