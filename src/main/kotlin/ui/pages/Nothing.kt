package ui.pages

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun NothingScreen() {


    var show by remember{ mutableStateOf(false) }


    Column {

        Button(
            onClick = {show = !show}
        ){
            Text("Test")
        }


        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){



            CardCustomAnimation(
                visible = show,
                enter = fadeIn(),
                exit = fadeOut(),
                label = "Fade"
            )


            CardCustomAnimation(
                visible = show,
                enter = slideInVertically(),
                exit = slideOutVertically(),
                label = "SlideVertically"
            )



            CardCustomAnimation(
                visible = show,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                label = "Fade + Slide"
            )

        }


    }

}


@Composable
fun CardCustomAnimation(visible: Boolean, enter: EnterTransition, exit: ExitTransition, label: String) {

    AnimatedVisibility(
        visible = visible,
        enter = enter,
        exit = exit
    ) {

        Card {

            Column (
                modifier = Modifier.padding(16.dp)
            ){

                Text(label)

            }


        }


    }


}



