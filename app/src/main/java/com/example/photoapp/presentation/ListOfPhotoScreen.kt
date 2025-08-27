package com.example.photoapp.presentation

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.entity.PhotoDtoItem
import com.example.domain.utils.Resource
import com.example.photoapp.viewmodel.PhotoDetailsViewModel
import com.google.gson.Gson
import com.example.photoapp.ui.theme.topAppBarBackgroundColor
import com.example.photoapp.ui.theme.topAppBarContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.photoapp.core.Constants.BACK
import com.example.photoapp.core.Constants.ERROR
import com.example.photoapp.core.Constants.NO_DESCRIPTION
import com.example.photoapp.core.Constants.NULL_DATA
import com.example.photoapp.core.Constants.PHOTO_APP
import com.example.photoapp.core.Screen

@Composable
fun ListOfPhotoScreen(
    viewModel: PhotoDetailsViewModel,
    navController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getListOfPhoto()
    }
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            DefaultAppBar(screen = Screen.PhotoList, navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            when (val pageState = state) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }
                is Resource.Error -> {
                    Text(
                        text = pageState.message.toString() ?: ERROR,
                        style = MaterialTheme.typography.h5,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Toast.makeText(
                        LocalContext.current,
                        pageState.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Success -> {
                    if (pageState.data != null) {
                        val foodItemList: List<PhotoDtoItem> = pageState.data!!
                        ListPhotos(foodItemList, navController)
                    } else {
                        Toast.makeText(
                            LocalContext.current,
                            NULL_DATA,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        }
    }
}

@Composable
fun ListPhotos(foodItemList: List<PhotoDtoItem>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(foodItemList) { foodItem ->
            Log.d("Item", foodItem.toString())
            PhotoCard(foodItem, navController = navController)
        }
    }
}

@Composable
fun PhotoCard(
    photoDtoItem: PhotoDtoItem,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                val foodDtoItemJson = Uri.encode(Gson().toJson(photoDtoItem))
                navController.navigate("photoItemDetail/$foodDtoItemJson")
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(photoDtoItem.urls.regular),
                contentDescription = photoDtoItem.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(4.dp))
            Text(text = photoDtoItem.alt_description ?: NO_DESCRIPTION)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(screen: Screen, navController: NavHostController) {
    when (screen) {
        Screen.PhotoList -> {
            TopAppBar(
                title = { Text(PHOTO_APP) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.topAppBarBackgroundColor,
                    titleContentColor = MaterialTheme.colors.topAppBarContentColor
                )

            )
        }

        Screen.PhotoItemDetails -> {
            TopAppBar(
                title = { Text(PHOTO_APP) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colors.topAppBarBackgroundColor,
                    titleContentColor = MaterialTheme.colors.topAppBarContentColor
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = BACK,
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }
            )
        }
    }

}