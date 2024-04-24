package kh.com.acleda.deposits.modules.home.data.repository

import android.content.Context
import com.google.gson.Gson
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.util.JsonFile
import kh.com.acleda.deposits.modules.home.domain.model.CATOpenTermModel
import kh.com.acleda.deposits.modules.home.domain.model.CATTermModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateDetailsModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRatesModel
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue3
import kh.com.acleda.deposits.ui.theme.Gold1
import kh.com.acleda.deposits.ui.theme.Gold2
import kh.com.acleda.deposits.ui.theme.Gold4
import kh.com.acleda.deposits.ui.theme.Gold5
import kh.com.acleda.deposits.ui.theme.Gold6
import kh.com.acleda.deposits.ui.theme.Green1
import kh.com.acleda.deposits.ui.theme.Green3
import kh.com.acleda.deposits.ui.theme.currentLanguage

/**
 * Pretend repository for DepositRate's data.
 */
object DepositRateRepo {
    private val gson = Gson()
    private val jsonFile = JsonFile()

    private fun getDepositRateData(context: Context): DepositRatesModel {
        // read json data from file
        // convert it into object model
        val fileName = "json/deposit_rate.json"
        val jsonString = jsonFile.loadJSONFile(context, fileName)

        return gson.fromJson(jsonString)
    }

    fun getCATOpenTerm(context: Context): CATOpenTermModel {
        val depositRate = getDepositRateData(context)

        val hiIncomeRate = depositRate.hiIncome
        val hiGrowthRate = depositRate.hiGrowth
        val longTermRate = depositRate.longTerm

        // rates in 1 Month
        val minRateHiIncome = hiIncomeRate?.rateDetails?.first()
        val minRateHiGrowth = hiGrowthRate?.rateDetails?.first()
        val minRateLongTerm = longTermRate?.rateDetails?.first()

        return CATOpenTermModel(
            hiIncome = CATTermModel(
                id = hiIncomeRate?.id ?: "",
                name = hiIncomeRate?.label ?: "",
                icon = R.drawable.ic_hi_income,
                minMonth = minRateHiIncome?.term?.toInt() ?: 1,
                minMonthRate = getCurrentTermRate(minRateHiIncome!!),
                mainColor = Green3,
                supportColor = Green1
            ),
            hiGrowth = CATTermModel(
                id = hiGrowthRate?.id ?: "",
                name = hiGrowthRate?.label ?: "",
                icon = R.drawable.ic_hi_growth,
                minMonth = minRateHiGrowth?.term?.toInt() ?: 1,
                minMonthRate = getCurrentTermRate(minRateHiGrowth!!),
                mainColor = Blue3,
                supportColor = Blue1
            ),
            longTerm = CATTermModel(
                id = longTermRate?.id ?: "",
                name = longTermRate?.label ?: "",
                icon = R.drawable.ic_long_term,
                minMonth = minRateLongTerm?.term?.toInt() ?: 36,
                minMonthRate = getCurrentTermRate(minRateLongTerm!!),
                mainColor = Gold6,
                supportColor = Gold4
            )
        )
    }

    private fun getCurrentTermRate(rate: DepositRateDetailsModel): Float {
        return when (currentLanguage.language) {
            "en" -> rate.usdRate
            "km" -> rate.usdRate
            else -> rate.usdRate
        }?.toFloat() ?: 0.0f
    }
}