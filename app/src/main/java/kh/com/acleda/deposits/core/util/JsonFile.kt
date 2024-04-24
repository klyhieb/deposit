package kh.com.acleda.deposits.core.util

import android.content.Context

class JsonFile {
    fun loadJSONFile(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

}