package com.example.photoapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.entity.PhotoDtoItem
import com.example.photoapp.core.Screen
import com.example.photoapp.core.Constants.EMPTY_STRING
import com.example.photoapp.core.Constants.LIKES
import com.example.photoapp.core.Constants.LINKS
import com.example.photoapp.core.Constants.NO_DESCRIPTION
import com.example.photoapp.core.Constants.PROTFOLIO_URL
import com.example.photoapp.core.Constants.TOTAL_PHOTOS
import com.example.photoapp.core.Constants.USER
import com.example.photoapp.core.Constants.USERNAME

@Composable
fun PhotoItemDetail(photoDtoItem: PhotoDtoItem, navController: NavHostController) {
    Scaffold(
        topBar = {
            DefaultAppBar(screen = Screen.PhotoItemDetails, navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(photoDtoItem.urls.regular),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = photoDtoItem.alt_description ?: NO_DESCRIPTION,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "$LIKES : ${photoDtoItem.likes.toString() ?: EMPTY_STRING}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "$LINKS : ${photoDtoItem.links.download_location ?: EMPTY_STRING}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(2.dp))
            val userDetails: String =
                " $USERNAME : ${photoDtoItem.user.username} , $TOTAL_PHOTOS ${photoDtoItem.user.total_photos} " +
                        ", $PROTFOLIO_URL ${photoDtoItem.user.portfolio_url} , ${photoDtoItem.user.links} "
            Text(
                text = "$USER : ${userDetails ?: ""} ",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}