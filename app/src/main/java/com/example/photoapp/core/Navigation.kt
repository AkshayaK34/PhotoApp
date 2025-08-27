package com.example.photoapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.domain.entity.PhotoDtoItem
import com.example.photoapp.presentation.PhotoItemDetail
import com.example.photoapp.presentation.ListOfPhotoScreen
import com.example.photoapp.viewmodel.PhotoDetailsViewModel
import com.google.gson.Gson

@Composable
fun SetUpNavigation(navController: NavHostController, foodDetailsViewModel: PhotoDetailsViewModel) {
    NavHost(navController = navController, startDestination = Screen.PhotoList.route) {
        composable(Screen.PhotoList.route) {
            ListOfPhotoScreen(foodDetailsViewModel, navController)
        }
        composable(
            route = Screen.PhotoItemDetails.route,
            arguments = listOf(navArgument("photoItem") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("photoItem")
            val photoItem = Gson().fromJson(json, PhotoDtoItem::class.java)
            PhotoItemDetail(photoItem, navController)
        }
    }
}

enum class Screen(val route: String) {
    PhotoList("photoDetails"),
    PhotoItemDetails("photoItemDetail/{photoItem}")
}
