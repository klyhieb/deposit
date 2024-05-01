package kh.com.acleda.deposits.modules.home.domain.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AccountListModel(
    @SerializedName("status") var status: String? = "",
    @SerializedName("msg") var msg: String? = "",
    @SerializedName("accountList") var accountList: ArrayList<AccountModel> = arrayListOf()
)

@Keep
data class AccountModel(
    @SerializedName("showAccNumber") var showAccNumber: String? = "",
    @SerializedName("accNumber") var accNumber: String? = "",
    @SerializedName("balanceAvailable") var balanceAvailable: String? = "",
    @SerializedName("balanceWorking") var balanceWorking: String? = "",
    @SerializedName("odType") var odType: String? = "",
    @SerializedName("accStatus") var accStatus: String? = "",
    @SerializedName("accType") var accType: String? = "",
    @SerializedName("ccy") var ccy: String? = "",
    @SerializedName("t24type") var t24type:  String?= "",
    @SerializedName("linkCorporate") var linkCorporate: String? = "",
    @SerializedName("originCcy") var originCcy: String? = ""
)