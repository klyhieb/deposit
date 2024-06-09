package kh.com.acleda.deposits.modules.home.data.repository

import android.content.Context
import com.google.gson.Gson
import kh.com.acleda.deposits.R
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.util.JsonFile
import kh.com.acleda.deposits.modules.home.domain.model.CATOpenTermModel
import kh.com.acleda.deposits.modules.home.domain.model.CATTermModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateDetailsModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRateObjectModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositRatesModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.Blue1
import kh.com.acleda.deposits.ui.theme.Blue3
import kh.com.acleda.deposits.ui.theme.Gold4
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

    private val depositRateModel: DepositRatesModel? = null

    private fun getDepositRateData(context: Context): DepositRatesModel {
        if (depositRateModel != null) return depositRateModel
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

    private fun getDepositRateByType(context: Context, termType: TermType): DepositRateObjectModel {
        val depositRate = getDepositRateData(context)

        return when (termType)
        {
            TermType.HI_INCOME -> depositRate.hiIncome!!
            TermType.HI_GROWTH -> depositRate.hiGrowth!!
            TermType.LONG_TERM -> depositRate.longTerm!!
            TermType.DEFAULT -> DepositRateObjectModel()
        }
    }

    fun getDepositRateByTypeAndCcy(context: Context, termType: TermType, ccy: CCY): DepositRateObjectModel {
        val rateByType = getDepositRateByType(context, termType)

        rateByType.rateDetails?.mapIndexed { index, rateDetail ->
            rateDetail.ccy = ccy
            rateDetail.index = index
            rateDetail.currentPA = getCurrentPAByCcy(rateDetail, ccy)
            rateDetail.currentPAString = getCurrentPAByCcyString(rateDetail, ccy)
            if (rateDetail.term == "1") {
                rateDetail.month = "Month"
            }
            rateDetail
        }

        return rateByType
    }

    private fun getCurrentPAByCcy(rate: DepositRateDetailsModel, ccy: CCY): Double {
        return when (ccy) {
            CCY.RIEL -> rate.khrRate?.toDoubleOrNull() ?: 0.0
            CCY.DOLLAR -> rate.usdRate?.toDoubleOrNull() ?: 0.0
            CCY.DEFAULT -> 0.0
        }
    }

    private fun getCurrentPAByCcyString(rate: DepositRateDetailsModel, ccy: CCY): String {
        return when (ccy) {
            CCY.RIEL -> "${rate.khrRate} p.a."
            CCY.DOLLAR -> "${rate.usdRate} p.a."
            CCY.DEFAULT -> ""
        }
    }
}