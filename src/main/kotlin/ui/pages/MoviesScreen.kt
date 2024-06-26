package ui.pages

import MovieDao
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import getDatabaseBuilder
import getRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionHome(daoMovie: MovieDao) {

    val list by daoMovie.getMovies().collectAsState(initial = emptyList())

    Column {
        Text(text = "Home")

        LazyRow {


            items(items = list, key = { movie -> movie.id }) { movie ->

                var visible by remember { mutableStateOf(true) }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CardMovie(
                        text = movie.name,
                        url = movie.url,
                        modifier = Modifier.padding(2.dp)
                            .size(200.dp, 200.dp)
                            .onClick {

                                CoroutineScope(Dispatchers.IO).launch {
                                    visible = false
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

    if (dialogShowing) {
        AlertDialog(
            onDismissRequest = {},
            text = {
                Column {


                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text("Nombre")
                        }
                    )

                    OutlinedTextField(
                        value = url,
                        onValueChange = { url = it },
                        label = {
                            Text("url")
                        }
                    )

                }

            },
            dismissButton = {
                TextButton(
                    onClick = { onDismiss() }
                ){
                    Text("Dismiss")
                }
            },
            confirmButton = {

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
                ){

                    Text("Confirm")
                }
            }
        )
    }



}
