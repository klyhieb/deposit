package kh.com.acleda.deposits.modules.depositList.data.repository

import android.content.Context
import com.google.gson.Gson
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.util.JsonFile
import kh.com.acleda.deposits.modules.depositList.domain.model.ViewTermDetailModel

/**
 * Pretend repository for ViewTermDetail's data.
 */
object ViewTermDetailRepo {
    private val gson = Gson()
    private val jsonFile = JsonFile()

    private val viewTermDetail: ViewTermDetailModel? = null

    fun getViewTermDetail(context: Context): ViewTermDetailModel {
        if (viewTermDetail != null) return viewTermDetail
        // read json data from file
        // convert it into object model
        val fileName = "json/detail/view_term_detail.json"
        val jsonString = jsonFile.loadJSONFile(context, fileName)

        return gson.fromJson(jsonString)
    }
}