package ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.ByteArrayInputStream


suspend fun loadImageFromImage(url: String): ImageBitmap {

    return HttpClient(CIO).use { client ->
        val byteArray = ByteArrayInputStream(client.get(url).readBytes())

        loadImageBitmap(byteArray)
    }
}
