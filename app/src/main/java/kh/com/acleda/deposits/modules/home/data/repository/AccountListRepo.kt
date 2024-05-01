package kh.com.acleda.deposits.modules.home.data.repository

import android.content.Context
import com.google.gson.Gson
import kh.com.acleda.deposits.core.fromJson
import kh.com.acleda.deposits.core.util.JsonFile
import kh.com.acleda.deposits.modules.home.domain.model.AccountListModel
import kh.com.acleda.deposits.modules.home.domain.model.AccountType
import kh.com.acleda.deposits.modules.home.domain.model.SelectAccountModel
import kh.com.acleda.deposits.modules.home.presentation.components.CCY

object AccountListRepo {
    private val gson = Gson()
    private val jsonFile = JsonFile()

    private val accountList: AccountListModel? = null

    private fun getAccountListData(context: Context): AccountListModel {
        if (accountList != null) return accountList
        // read json data from file
        // convert it into object model
        val fileName = "json/account_list.json"
        val jsonString = jsonFile.loadJSONFile(context, fileName)

        return gson.fromJson(jsonString)
    }

    fun getAccountOnBottomSheet(context: Context): List<SelectAccountModel> {
        val accountList = getAccountListData(context)

        return accountList.accountList.map {
            SelectAccountModel(
                accountNumber = it.showAccNumber ?: "",
                balance = getBalanceFromStr(it.balanceAvailable),
                currency = getAccountCurrency(it.ccy ?: ""),
                type = getAccountType(it.accType ?: "")
            )
        }
    }

    private fun getBalanceFromStr(strBalance: String?): Float {
        val strNum = strBalance?.replace(",", "")
        return strNum?.toFloatOrNull() ?: 0.0f
    }

    private fun getAccountCurrency(ccy: String): CCY {
        return when(ccy.uppercase()) {
            CCY.RIEL.dec.uppercase() -> CCY.RIEL
            CCY.DOLLAR.dec.uppercase() -> CCY.DOLLAR
            else -> CCY.DEFAULT
        }
    }

    private fun getAccountType(type: String): AccountType {
        return when(type) {
            AccountType.WALLET.dec -> AccountType.WALLET
            AccountType.BANK.dec -> AccountType.BANK
            else -> AccountType.DEFAULT
        }
    }


}
