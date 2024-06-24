package ui.pages

import MovieDao
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
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


    val db = remember {  getRoomDatabase(getDatabaseBuilder()) }

    Column {

        SectionHome(db.MovieDao())

        SectionFamily(db.MovieDao())

    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionHome(daoMovie: MovieDao) {

    val list by daoMovie.getMovies().collectAsState(initial = emptyList())

    Column {
        Text(text="Home")
        LazyRow{


            items(items = list, key = {movie -> movie.id}) { movie ->

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
fun SectionFamily(daoMovie: MovieDao) {

    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    Column {


        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {
                Text("Nombre")
            }
        )

        OutlinedTextField(
            value = url,
            onValueChange = { url = it},
            label = {
                Text("url")
            }
        )


        Button(
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

            },
            enabled = name.isNotBlank().and(url.isNotBlank())
        ){
            Text("Add")
        }

    }

}
