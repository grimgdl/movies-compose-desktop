import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ui.pages.LoginScreen
import ui.pages.MoviesScreen
import ui.pages.NothingScreen

@Composable
@Preview
fun App() {
    var screen by remember { mutableStateOf(Screens.Movies) }

    MaterialTheme {

        Surface(
            modifier = Modifier.padding(16.dp)
                .fillMaxSize()
        ) {


            Column {

                Row {

                    Button(onClick = {
                        screen = Screens.Movies
                    }) {
                        Text("Movies")
                    }

                    Button(onClick = {
                        screen = Screens.Nothing

                    } ){
                        Text("Nothing")
                    }

                    Button(onClick = {
                        screen = Screens.Login
                    } ){
                        Text("Log in")
                    }


                }


                when(screen) {
                    Screens.Movies -> MoviesScreen()
                    Screens.Nothing -> NothingScreen()
                    Screens.Login -> LoginScreen()
                }


            }

        }



    }
}



enum class Screens {
    Movies,
    Login,
    Nothing

}


fun main() = application {


    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(position = WindowPosition(alignment = Alignment.Center))
    ) {
        App()
    }
}
