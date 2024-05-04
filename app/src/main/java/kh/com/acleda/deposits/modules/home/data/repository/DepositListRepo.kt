package kh.com.acleda.deposits.modules.home.data.repository

import android.content.Context
import com.google.gson.Gson
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.util.ExchangeRate
import kh.com.acleda.deposits.core.util.JsonFile
import kh.com.acleda.deposits.modules.home.domain.model.AccountListModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositListModel
import kh.com.acleda.deposits.modules.home.domain.model.DepositTypeModel
import kh.com.acleda.deposits.modules.home.domain.model.SummaryCurrencyModel
import kh.com.acleda.deposits.modules.home.domain.model.SummaryDepositModel
import kh.com.acleda.deposits.modules.home.domain.model.TermAmountModel
import kh.com.acleda.deposits.modules.home.domain.model.TermType
import kh.com.acleda.deposits.modules.home.presentation.components.CCY
import kh.com.acleda.deposits.ui.theme.Blue7
import kh.com.acleda.deposits.ui.theme.Gold7
import kh.com.acleda.deposits.ui.theme.Gray0
import kh.com.acleda.deposits.ui.theme.Green2
import kh.com.acleda.deposits.ui.theme.Green7
import kh.com.acleda.deposits.ui.theme.Red2

/**
 * Pretend repository for DepositList's data.
 */
object DepositListRepo {
    private val gson = Gson()
    private val jsonFile = JsonFile()
    private val exchangeRate = ExchangeRate()

    private val depositList: DepositListModel? = null

    private fun getDepositListData(context: Context): DepositListModel {
        if (depositList != null) return depositList
        // read json data from file
        // convert it into object model
        val fileName = "json/deposit_list.json"
        val jsonString = jsonFile.loadJSONFile(context, fileName)

        return gson.fromJson(jsonString)
    }

    fun getSummaryTermDeposit(context: Context): SummaryDepositModel {
        val depositList = getDepositListData(context)

        // total in KHR
        val totalAmountInRiel = depositList.listMM
                .filter{ it.currency == CCY.RIEL.dec.uppercase() }
            .map { it.AmountOri }.sum()

        // total in US
        val totalAmountInDollar = depositList.listMM
            .filter{ it.currency == CCY.DOLLAR.dec.uppercase() }
            .map { it.AmountOri }.sum()


        // Hi-Income
        val totalHiIncomeInDollar = depositList.listMM.filter {
            it.termTypeId == TermType.HI_INCOME.id && it.currency == CCY.DOLLAR.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        val totalHiIncomeInRiel = depositList.listMM.filter {
            it.termTypeId == TermType.HI_INCOME.id && it.currency == CCY.RIEL.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        // Hi-Growth
        val totalHiGrowthInDollar = depositList.listMM.filter {
            it.termTypeId == TermType.HI_GROWTH.id && it.currency == CCY.DOLLAR.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        val totalHiGrowthInRiel = depositList.listMM.filter {
            it.termTypeId == TermType.HI_GROWTH.id && it.currency == CCY.RIEL.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        // Long-Term
        val totalLongTermInDollar = depositList.listMM.filter {
            it.termTypeId == TermType.LONG_TERM.id && it.currency == CCY.DOLLAR.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        val totalLongTermInRiel = depositList.listMM.filter {
            it.termTypeId == TermType.LONG_TERM.id && it.currency == CCY.RIEL.dec.uppercase()
        }.map {
            it.AmountOri
        }.sum()

        return SummaryDepositModel(
            summaryByCurrency = listOf(
                SummaryCurrencyModel(
                    name = "KHR",
                    amountModel = TermAmountModel(
                        color = Green2,
                        ccy = CCY.RIEL,
                        amount = totalAmountInRiel
                    )
                ),
                SummaryCurrencyModel(
                    name = "US",
                    amountModel = TermAmountModel(
                        color = Red2,
                        ccy = CCY.DOLLAR,
                        amount = totalAmountInDollar
                    )
                )
            ),
            summaryInDollarByTypes = listOf(
                DepositTypeModel(
                    termType = TermType.HI_INCOME,
                    color = Gold7,
                    summaryAmountInDollar = totalHiIncomeInDollar + exchangeRate.riel2Dollar(
                        totalHiIncomeInRiel
                    )
                ),
                DepositTypeModel(
                    termType = TermType.HI_GROWTH,
                    color = Blue7,
                    summaryAmountInDollar = totalHiGrowthInDollar + exchangeRate.riel2Dollar(
                        totalHiGrowthInRiel
                    )
                ),
                DepositTypeModel(
                    termType = TermType.LONG_TERM,
                    color = Green7,
                    summaryAmountInDollar = totalLongTermInDollar + exchangeRate.riel2Dollar(
                        totalLongTermInRiel
                    )
                )
            ),

            summaryByTypes = listOf(
                DepositTypeModel(
                    termType = TermType.HI_INCOME,
                    color = Gold7,
                    listOf(
                        TermAmountModel(
                            color = Gold7,
                            ccy = CCY.RIEL,
                            amount = totalHiIncomeInRiel
                        ),
                        TermAmountModel(
                            color = Gray0,
                            ccy = CCY.DOLLAR,
                            amount = totalHiIncomeInDollar
                        )
                    )
                ),
                DepositTypeModel(
                    termType = TermType.HI_GROWTH,
                    color = Blue7,
                    listOf(
                        TermAmountModel(
                            color = Blue7,
                            ccy = CCY.RIEL,
                            amount = totalHiGrowthInRiel
                        ),
                        TermAmountModel(
                            color = Gray0,
                            ccy = CCY.DOLLAR,
                            amount = totalHiGrowthInDollar
                        )
                    )
                ),
                DepositTypeModel(
                    termType = TermType.LONG_TERM,
                    color = Green7,
                    listOf(
                        TermAmountModel(
                            color = Green7,
                            ccy = CCY.RIEL,
                            amount = totalLongTermInRiel
                        ),
                        TermAmountModel(
                            color = Gray0,
                            ccy = CCY.DOLLAR,
                            amount = totalLongTermInDollar
                        )
                    )
                )
            )
        )
    }

    fun getDepositList(context: Context) = getDepositListData(context)
}
