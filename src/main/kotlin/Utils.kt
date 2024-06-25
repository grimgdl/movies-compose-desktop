import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object Utils {

    const val KEY = "122fb00f"

    const val URL = "http://www.omdbapi.com/?apikey=[yourkey]&"



    fun createDefaultProperties(filePath: String) {
        val file = File(filePath)

        if (!file.exists()) {
            val properties = Properties().apply {
                setProperty("window.position.x", "100")
                setProperty("window.position.y", "100")
            }

            saveProperties(filePath, properties)
        }


    }


    fun loadProperties(filePath: String): Properties {
        val properties = Properties()
        FileInputStream(filePath).use { inputStream ->

            properties.load(inputStream)

        }

        return properties
    }


    private fun saveProperties(filePath: String, properties: Properties) {
        FileOutputStream(filePath).use { outputStream ->
            properties.store(outputStream, null)

        }
    }

    fun updateProperty(filePath: String, key: String, value: String) {
        val properties = loadProperties(filePath)
        properties.setProperty(key, value)
        saveProperties(filePath, properties)
    }


}