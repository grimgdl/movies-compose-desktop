package ui.pages

import MovieDao
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import getDatabaseBuilder
import getRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.Movie
import ui.CardMovie


@Composable
fun MoviesScreen() {


    val db = remember { getRoomDatabase(getDatabaseBuilder()) }
    var dialogShowing by remember { mutableStateOf(false) }

    
    Box (
        modifier = Modifier.fillMaxSize()
    ){


        Column {

            SectionHome(db.MovieDao())

            AlertMovies(
                daoMovie = db.MovieDao(),
                dialogShowing = dialogShowing,
                onConfirm = {
                    dialogShowing = false
                },
                onDismiss = {
                    dialogShowing = false
                }

            )

        }

        FloatingActionButton(
            onClick = {
                dialogShowing = true
            },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
                .padding(16.dp)
        ){
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }

    }



}


@Composable
fun SectionHome(daoMovie: MovieDao) {

    val list by daoMovie.getMovies().collectAsState(initial = emptyList())


    Column {
        Text(text = "Home")

        LazyRow {


            items(items = list, key = { movie -> movie.id }) { movie ->

                var visible by remember { mutableStateOf(true) }

                var animationIn by remember { mutableStateOf(false) }

                LaunchedEffect(movie.id) {

                    animationIn = true
                }

                AnimatedVisibility(
                    visible = visible && animationIn,
                    enter = fadeIn() + slideInVertically(
                        animationSpec = tween()
                    ),
                    exit = fadeOut()
                ) {
                    CardMovie(
                        text = movie.name,
                        url = movie.url,
                        modifier = Modifier.padding(2.dp)
                            .size(200.dp, 200.dp),
                        onClickRemove = {
                            visible = false

                            CoroutineScope(Dispatchers.IO).launch {
                                delay(300)
                                daoMovie.removeMovie(movie)

                            }
                        }
                    )
                }


            }


        }
    }
}







@Composable
fun AlertMovies(
    daoMovie: MovieDao,
    dialogShowing: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }


    if (dialogShowing){
        Dialog(
            onDismissRequest = {onDismiss()},
            properties = DialogProperties()
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 24.dp
            ) {

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                ){

                    Text("New movie")

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text("Nombre")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        label = {
                            Text("url")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = { onDismiss() }
                        ){
                            Text("Dismiss")
                        }

                        TextButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {

                                    daoMovie.insertMovie(
                                        Movie(
                                            name = name,
                                            url = url
                                        )
                                    )

                                    name = ""
                                    url = ""


                                }
                                onConfirm()

                            },
                            enabled = name.isNotBlank().and(url.isNotBlank())
                        ) {
                            Text("Create")
                        }
                    }
                }






            }


        }
    }



}
