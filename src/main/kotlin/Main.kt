import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import ui.CustomTheme
import ui.pages.LoginScreen
import ui.pages.MoviesScreen
import ui.pages.NothingScreen
import javax.swing.JFrame

@Composable
@Preview
fun App(window: ComposeWindow, onCloseRequest: () -> Unit) {
    var screen by remember { mutableStateOf(Screens.Movies) }

    CustomTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            Column{

                if (window.isUndecorated ){
                    CustomTitlebar(
                        onCloseRequest = onCloseRequest,
                        window = window
                    )
                }

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
fun CustomTitlebar(onCloseRequest: () -> Unit, window: ComposeWindow) {

    var positionX by remember { mutableStateOf(0f) }
    var positionY by remember { mutableStateOf(0f) }


    Box(
        modifier = Modifier.fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 8.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    positionX += dragAmount.x
                    positionY += dragAmount.y

                    val frame = (window as JFrame)

                    frame.setLocation((frame.x + positionX.toInt()), (frame.y  + positionY.toInt()))

                }
            },
        contentAlignment = Alignment.CenterStart
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("My Application")
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
        App(window = window, onCloseRequest = ::exitApplication)


    }
}
