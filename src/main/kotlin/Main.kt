import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import ui.pages.LoginScreen
import ui.pages.MoviesScreen
import ui.pages.NothingScreen

@Composable
@Preview
fun App(onCloseRequest: () -> Unit) {
    var screen by remember { mutableStateOf(Screens.Movies) }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {

            Column{

                CustomTitlebar(
                    onCloseRequest = onCloseRequest
                )

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row {

                        Button(onClick = {
                            screen = Screens.Movies
                        }) {
                            Text("Movies")
                        }

                        Button(onClick = {
                            screen = Screens.Nothing

                        } ){
                            Text("Animations")
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
}



enum class Screens {
    Movies,
    Login,
    Nothing

}


@Composable
fun CustomTitlebar(onCloseRequest: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(40.dp)
            .background(Color.DarkGray)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("My Application" , color = Color.Gray)
            IconButton(
                onClick =  {
                    onCloseRequest()
                }
            ){
                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
            }

        }

    }
}


fun main() = application {

    val os = remember { Utils.getOsName() }

    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(position = WindowPosition(alignment = Alignment.Center)),
        undecorated = os != "MacOS"
    ) {
        App(onCloseRequest = ::exitApplication)

    }
}
