package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun CardMovie(

    text: String,
    url: String?,
    modifier: Modifier = Modifier,
    onClickRemove: () -> Unit
) {


    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }


    LaunchedEffect(url) {


        withContext(Dispatchers.IO) {
            imageBitmap = url?.let { loadImageFromImage(it) }
        }

    }

    Card(
        modifier = modifier
    ) {

        Column {


            Box(

                modifier = Modifier.fillMaxWidth()
                    .height(120.dp)
            ) {

                imageBitmap?.let {bitmap->
                    Image(
                        bitmap= bitmap,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }


                IconButton(
                    onClick = onClickRemove,
                    modifier = Modifier.align(alignment = Alignment.TopEnd),
                ){
                    Icon(imageVector = Icons.Sharp.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

            }


            Text(text = text, modifier = Modifier.padding(8.dp))


        }


    }



}