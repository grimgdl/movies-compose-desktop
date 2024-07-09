package ui.pages

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

            CardCustomAnimation(
                visible = show,
                enter = scaleIn(),
                exit = scaleOut(),
                label = "Scale"
            )


        }



        ListComponent()



    }

}


@Composable
fun ListComponent() {


    var list by remember { mutableStateOf(mutableListOf("hola 1", "hola 2", "hola 3", "hola 4")) }


    LazyColumn {

        items(items = list) { hola ->

            Card(
                modifier = Modifier.clickable {

                    list = list.toMutableList().apply {
                        remove(hola)
                    }

                }
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(hola)
                }


            }


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



