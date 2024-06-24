import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
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
        onCloseRequest = ::exitApplication
    ) {

        App()
    }
}
