package am.leon.utilities.android.helpers.properties

import android.content.Context
import java.io.IOException
import java.util.Locale
import java.util.Properties

class ConfigurationUtil(private val context: Context, private val configFile: IConfigurationFile) {

    private var properties = Properties()

    fun getProperty(key: IConfigurationKey): String {
        val keyValue = getPropertiesFile().getProperty(key.key, "")

        return keyValue.ifEmpty {
            throw NoSuchElementException(
                String.format(
                    Locale.US,
                    "Trying to get property 'key' \"%s\", but the key is empty or not found.",
                    key.key
                )
            )
        }
    }

    private fun getPropertiesFile(): Properties {
        try {
            val inputStream = context.assets.open(configFile.fileName)
            properties.load(inputStream)
            return properties
        } catch (e: IOException) {
            throw IOException("\"${configFile.fileName}\" file is not found. Please add this file to assets folder in your project")
        }
    }
}